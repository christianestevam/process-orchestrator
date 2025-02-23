package com.ufc.reuso.processorchestrator.dto;

import com.ufc.reuso.processorchestrator.model.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class OrderResponseDTO {
    private UUID id;
    private String customerName;
    private String customerEmail;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItemResponseDTO> items;
    private PaymentResponseDTO payment;
    private InvoiceResponseDTO invoice;
}