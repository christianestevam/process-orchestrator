package com.ufc.reuso.processorchestrator.events;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class PaymentProcessedEvent {
    private final UUID orderId;
    private boolean paymentSuccessful;
    

}