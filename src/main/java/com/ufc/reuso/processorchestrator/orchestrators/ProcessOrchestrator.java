package com.ufc.reuso.processorchestrator.orchestrators;

import com.ufc.reuso.processorchestrator.events.*;
import com.ufc.reuso.processorchestrator.messaging.EventPublisher;

import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class ProcessOrchestrator {

    private final EventPublisher eventPublisher;

    public ProcessOrchestrator(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    // Inicia o fluxo de processamento do pedido
    public void startOrderProcessing(UUID orderId) {
        System.out.println("Iniciando o processamento do pedido: " + orderId);

        // Publica evento de validação de estoque
        StockValidatedEvent stockEvent = new StockValidatedEvent(orderId, false);
        eventPublisher.publishEvent("stock-validation-queue", stockEvent);
    }

    // Escuta o evento de estoque validado
    @RabbitListener(queues = "stock-validation-queue")
    public void handleStockValidated(StockValidatedEvent event) {
        if (event.isStockAvailable()) {
            System.out.println("Estoque validado para o pedido: " + event.getOrderId());

            // Publica evento de processamento de pagamento
            PaymentProcessedEvent paymentEvent = new PaymentProcessedEvent(event.getOrderId(), false);
            eventPublisher.publishEvent("payment-processing-queue", paymentEvent);
        } else {
            System.out.println("Falha na validação de estoque para o pedido: " + event.getOrderId());
            retry("stock-validation-queue", event);
        }
    }

    // Escuta o evento de pagamento processado
    @RabbitListener(queues = "payment-processing-queue")
    public void handlePaymentProcessed(PaymentProcessedEvent event) {
        if (event.isPaymentSuccessful()) {
            System.out.println("Pagamento confirmado para o pedido: " + event.getOrderId());

            // Publica evento de geração de nota fiscal
            InvoiceGeneratedEvent invoiceEvent = new InvoiceGeneratedEvent(event.getOrderId(), false, null);
            eventPublisher.publishEvent("invoice-generation-queue", invoiceEvent);
        } else {
            System.out.println("Falha no pagamento do pedido: " + event.getOrderId());
            retry("payment-processing-queue", event);
        }
    }

    // Escuta o evento de nota fiscal gerada
    @RabbitListener(queues = "invoice-generation-queue")
    public void handleInvoiceGenerated(InvoiceGeneratedEvent event) {
        if (event.isInvoiceGenerated()) {
            System.out.println("Nota fiscal gerada com sucesso! Número: " + event.getInvoiceNumber());
        } else {
            System.out.println("Falha ao gerar nota fiscal para o pedido: " + event.getOrderId());
            retry("invoice-generation-queue", event);
        }
    }

    // Estratégia de retry simples
    private void retry(String queue, Object event) {
        System.out.println("Tentando novamente o processamento em " + queue);
        eventPublisher.publishEvent(queue, event);
    }
}
