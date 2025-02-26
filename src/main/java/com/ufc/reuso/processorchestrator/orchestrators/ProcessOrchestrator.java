package com.ufc.reuso.processorchestrator.orchestrators;

import com.ufc.reuso.processorchestrator.events.*;
import com.ufc.reuso.processorchestrator.messaging.EventPublisher;
import com.ufc.reuso.processorchestrator.model.Order;
import com.ufc.reuso.processorchestrator.model.OrderStatus;
import com.ufc.reuso.processorchestrator.repository.OrderRepository;

import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProcessOrchestrator {

    private final EventPublisher eventPublisher;
    private final OrderRepository orderRepository;

    public ProcessOrchestrator(EventPublisher eventPublisher, OrderRepository orderRepository) {
        this.eventPublisher = eventPublisher;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void startOrderProcessing(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Pedido já processado");
        }

        System.out.println("Iniciando o processamento do pedido: " + orderId);
        order.setStatus(OrderStatus.VALIDATING_STOCK);
        orderRepository.save(order);

        // Publicar evento para validação de estoque
        eventPublisher.publishEvent("order.stock.validation", new StockValidatedEvent(orderId, false));
    }

    @RabbitListener(queues = "order.stock.validated")
    public void handleStockValidated(StockValidatedEvent event) {
        Order order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (event.isStockAvailable()) {
            System.out.println("Estoque validado para o pedido: " + event.getOrderId());

            order.setStatus(OrderStatus.PAYMENT_PROCESSING);
            orderRepository.save(order);

            // Publicar evento para processamento de pagamento
            eventPublisher.publishEvent("order.payment.processing", new PaymentProcessedEvent(event.getOrderId(), false));
        } else {
            System.out.println("Falha na validação de estoque para o pedido: " + event.getOrderId());
            order.setStatus(OrderStatus.FAILED);
            orderRepository.save(order);
        }
    }

    @RabbitListener(queues = "order.payment.processing")
    public void handlePaymentProcessed(PaymentProcessedEvent event) {
        Order order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (event.isPaymentSuccessful()) {
            System.out.println("Pagamento confirmado para o pedido: " + event.getOrderId());

            order.setStatus(OrderStatus.INVOICE_GENERATION);
            orderRepository.save(order);

            // Publicar evento para geração de nota fiscal
            eventPublisher.publishEvent("order.invoice.generation", new InvoiceGeneratedEvent(event.getOrderId(), false, null));
        } else {
            System.out.println("Falha no pagamento do pedido: " + event.getOrderId());
            order.setStatus(OrderStatus.FAILED);
            orderRepository.save(order);
        }
    }

    @RabbitListener(queues = "order.invoice.generation")
    public void handleInvoiceGenerated(InvoiceGeneratedEvent event) {
        Order order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (event.isInvoiceGenerated()) {
            System.out.println("Nota fiscal gerada com sucesso! Número: " + event.getInvoiceNumber());

            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
        } else {
            System.out.println("Falha ao gerar nota fiscal para o pedido: " + event.getOrderId());
            order.setStatus(OrderStatus.FAILED);
            orderRepository.save(order);
        }
    }
}
