package com.ufc.reuso.processorchestrator.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.ufc.reuso.processorchestrator.events.InvoiceGeneratedEvent;
import com.ufc.reuso.processorchestrator.events.PaymentProcessedEvent;
import com.ufc.reuso.processorchestrator.messaging.EventPublisher;

@Service
public class PaymentProcessingService {

    private final EventPublisher eventPublisher;

    public PaymentProcessingService(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @RabbitListener(queues = "payment-processing-queue")
    public void handlePaymentProcessing(PaymentProcessedEvent event) {
        if (event.isPaymentSuccessful()) {
            System.out.println("Pagamento processado com sucesso para o pedido: " + event.getOrderId());

            // Publicar evento de geração de nota fiscal
            InvoiceGeneratedEvent invoiceEvent = new InvoiceGeneratedEvent(event.getOrderId(), true, null);
            eventPublisher.publishEvent("invoice-generation-queue", invoiceEvent);

        } else {
            System.out.println("Falha no pagamento para o pedido: " + event.getOrderId());
        }
    }
}