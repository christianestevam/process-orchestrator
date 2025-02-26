package com.ufc.reuso.processorchestrator.service.steps;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.ufc.reuso.processorchestrator.events.InvoiceGeneratedEvent;
import com.ufc.reuso.processorchestrator.messaging.EventPublisher;
import com.ufc.reuso.processorchestrator.repository.OrderRepository;
import com.ufc.reuso.processorchestrator.model.Order;
import com.ufc.reuso.processorchestrator.model.OrderStatus;

import java.util.UUID;

@Service
public class Step3_InvoiceGeneration {

    private final EventPublisher eventPublisher;
    private final OrderRepository orderRepository;

    public Step3_InvoiceGeneration(EventPublisher eventPublisher, OrderRepository orderRepository) {
        this.eventPublisher = eventPublisher;
        this.orderRepository = orderRepository;
    }

    @RabbitListener(queues = "order.invoice.generation")
    public void handleInvoiceGeneration(InvoiceGeneratedEvent event) {
        Order order = orderRepository.findById(event.getOrderId()).orElse(null);
        if (order == null) {
            System.err.println("Pedido não encontrado: " + event.getOrderId());
            return;
        }

        String invoiceNumber = "INV-" + UUID.randomUUID();
        System.out.println("Nota fiscal gerada: " + invoiceNumber + " para o pedido: " + event.getOrderId());

        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);

        eventPublisher.publishEvent("order.invoice.generated", new InvoiceGeneratedEvent(event.getOrderId(), true, invoiceNumber));
    }
}
