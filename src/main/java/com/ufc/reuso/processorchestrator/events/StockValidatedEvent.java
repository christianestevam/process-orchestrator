package com.ufc.reuso.processorchestrator.events;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class StockValidatedEvent {
        private final UUID orderId;
        private boolean stockAvailable;
    
}
