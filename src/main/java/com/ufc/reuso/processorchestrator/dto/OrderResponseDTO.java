package com.ufc.reuso.processorchestrator.dto;

import com.ufc.reuso.processorchestrator.model.Order;
import com.ufc.reuso.processorchestrator.model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
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
    // Construtor que recebe um objeto Order
    public OrderResponseDTO(Order order) {
        this.id = order.getId();
        this.customerName = order.getCustomerName();
        this.customerEmail = order.getCustomerEmail();
        this.status = order.getStatus();
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
        
        // Mapear os itens do pedido
        this.items = order.getItems().stream()
                .map(OrderItemResponseDTO::new)  // Converte cada OrderItem em um OrderItemDTO
                .collect(Collectors.toList());
        
        // Mapear Payment e Invoice se existirem
        this.payment = order.getPayment() != null ? new PaymentResponseDTO(order.getPayment()) : null;
        this.invoice = order.getInvoice() != null ? new InvoiceResponseDTO(order.getInvoice()) : null;
    }
}