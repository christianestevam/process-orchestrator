package com.ufc.reuso.processorchestrator.service.steps;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.ufc.reuso.processorchestrator.events.InvoiceGeneratedEvent;
import com.ufc.reuso.processorchestrator.events.PaymentProcessedEvent;
import com.ufc.reuso.processorchestrator.messaging.EventPublisher;
import com.ufc.reuso.processorchestrator.repository.OrderRepository;
import com.ufc.reuso.processorchestrator.model.Order;
import com.ufc.reuso.processorchestrator.model.OrderStatus;

@Service
public class Step2_PaymentProcessing {

    private final EventPublisher eventPublisher;
    private final OrderRepository orderRepository;

    public Step2_PaymentProcessing(EventPublisher eventPublisher, OrderRepository orderRepository) {
        this.eventPublisher = eventPublisher;
        this.orderRepository = orderRepository;
    }

    @RabbitListener(queues = "order.payment.processing")
    public void handlePaymentProcessing(PaymentProcessedEvent event) {
        Order order = orderRepository.findById(event.getOrderId()).orElse(null);
        if (order == null) {
            System.err.println("Pedido não encontrado: " + event.getOrderId());
            return;
        }

        if (event.isPaymentSuccessful()) {
            System.out.println("Pagamento aprovado para o pedido: " + event.getOrderId());
            order.setStatus(OrderStatus.INVOICE_GENERATION);
            orderRepository.save(order);

            eventPublisher.publishEvent("order.invoice.generation", new InvoiceGeneratedEvent(event.getOrderId(), false, null));
        } else {
            System.out.println("Falha no pagamento do pedido: " + event.getOrderId());
            order.setStatus(OrderStatus.FAILED);
            orderRepository.save(order);
        }
    }
}
