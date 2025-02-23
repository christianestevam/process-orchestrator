package com.ufc.reuso.processorchestrator.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.ufc.reuso.processorchestrator.events.PaymentProcessedEvent;
import com.ufc.reuso.processorchestrator.events.StockValidatedEvent;
import com.ufc.reuso.processorchestrator.messaging.EventPublisher;

@Service
public class StockValidationService {

    private final EventPublisher eventPublisher;

    public StockValidationService(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @RabbitListener(queues = "stock-validation-queue")
    public void handleStockValidation(StockValidatedEvent event) {
        if (event.isStockAvailable()) {
            System.out.println("Estoque validado com sucesso para o pedido: " + event.getOrderId());

            // Publicar evento de pagamento
            PaymentProcessedEvent paymentEvent = new PaymentProcessedEvent(event.getOrderId(), true);
            eventPublisher.publishEvent("payment-processing-queue", paymentEvent);

        } else {
            System.out.println("Estoque insuficiente para o pedido: " + event.getOrderId());
        }
    }
}