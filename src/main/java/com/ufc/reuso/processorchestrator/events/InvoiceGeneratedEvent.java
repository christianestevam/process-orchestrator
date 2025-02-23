package com.ufc.reuso.processorchestrator.events;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class InvoiceGeneratedEvent {
    private final UUID orderId;
    private boolean isInvoiceGenerated;
    private String invoiceNumber;
}