package com.ufc.reuso.processorchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import com.ufc.reuso.processorchestrator.model.OrderItem;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
// Representa o dto de resposta de item de pedido
public class OrderItemResponseDTO {
    // Atributos retornados na resposta
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    // Construtor que recebe um objeto OrderItem
    public OrderItemResponseDTO(OrderItem orderItem) {
        this.productName = orderItem.getProductName();
        this.quantity = orderItem.getQuantity();
        this.unitPrice = orderItem.getUnitPrice();
        this.totalPrice = orderItem.getTotalPrice();
    }
}