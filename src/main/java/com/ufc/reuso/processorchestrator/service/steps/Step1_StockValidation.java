package com.ufc.reuso.processorchestrator.service.steps;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.ufc.reuso.processorchestrator.events.StockValidatedEvent;
import com.ufc.reuso.processorchestrator.events.PaymentProcessedEvent;
import com.ufc.reuso.processorchestrator.messaging.EventPublisher;
import com.ufc.reuso.processorchestrator.repository.OrderRepository;
import com.ufc.reuso.processorchestrator.model.Order;
import com.ufc.reuso.processorchestrator.model.OrderStatus;

@Service
public class Step1_StockValidation {

    private final EventPublisher eventPublisher;
    private final OrderRepository orderRepository;

    public Step1_StockValidation(EventPublisher eventPublisher, OrderRepository orderRepository) {
        this.eventPublisher = eventPublisher;
        this.orderRepository = orderRepository;
    }

    @RabbitListener(queues = "order.stock.validated")
    public void handleStockValidation(StockValidatedEvent event) {
        Order order = orderRepository.findById(event.getOrderId()).orElse(null);
        if (order == null) {
            System.err.println("Pedido não encontrado: " + event.getOrderId());
            return;
        }

        if (event.isStockAvailable()) {
            System.out.println("Estoque validado para o pedido: " + event.getOrderId());
            order.setStatus(OrderStatus.PAYMENT_PROCESSING);
            orderRepository.save(order);

            eventPublisher.publishEvent("order.payment.processing", new PaymentProcessedEvent(event.getOrderId(), true));
        } else {
            System.out.println("Estoque insuficiente para o pedido: " + event.getOrderId());
            order.setStatus(OrderStatus.FAILED);
            orderRepository.save(order);
        }
    }
}
