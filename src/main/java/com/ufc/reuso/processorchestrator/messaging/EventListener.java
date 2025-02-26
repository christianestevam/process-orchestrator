package com.ufc.reuso.processorchestrator.messaging;

import com.ufc.reuso.processorchestrator.events.StockValidatedEvent;
import com.ufc.reuso.processorchestrator.events.PaymentProcessedEvent;
import com.ufc.reuso.processorchestrator.events.InvoiceGeneratedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventListener {

    @RabbitListener(queues = "order.stock.validated")
    public void handleStockValidated(StockValidatedEvent event) {
        System.out.println("Evento recebido - Estoque validado: " + event.getOrderId() + " | Disponível: " + event.isStockAvailable());
    }

    @RabbitListener(queues = "order.payment.processed")
    public void handlePaymentProcessed(PaymentProcessedEvent event) {
        System.out.println("Evento recebido - Pagamento processado: " + event.getOrderId() + " | Sucesso: " + event.isPaymentSuccessful());
    }

    @RabbitListener(queues = "order.invoice.generated")
    public void handleInvoiceGenerated(InvoiceGeneratedEvent event) {
        System.out.println("Evento recebido - Nota fiscal gerada: " + event.getOrderId() + " | Número: " + event.getInvoiceNumber());
    }
}
