package com.ufc.reuso.processorchestrator.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.ufc.reuso.processorchestrator.events.InvoiceGeneratedEvent;
import com.ufc.reuso.processorchestrator.messaging.EventPublisher;

import java.util.UUID;

@Service
public class InvoiceGenerationService {

    private final EventPublisher eventPublisher;

    public InvoiceGenerationService(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @RabbitListener(queues = "invoice-generation-queue")
    public void handleInvoiceGeneration(InvoiceGeneratedEvent event) {
        if (!event.isInvoiceGenerated()) {
            // Simular geração de número de nota fiscal
            String invoiceNumber = UUID.randomUUID().toString();

            System.out.println("Nota fiscal gerada para o pedido: " + event.getOrderId() + " - Número: " + invoiceNumber);

            // Publicar evento de nota fiscal gerada com sucesso
            InvoiceGeneratedEvent completedEvent = new InvoiceGeneratedEvent(event.getOrderId(), true, invoiceNumber);
            eventPublisher.publishEvent("invoice-completed-queue", completedEvent);
        }
    }
}