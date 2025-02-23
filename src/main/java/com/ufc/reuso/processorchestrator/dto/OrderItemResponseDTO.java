package com.ufc.reuso.processorchestrator.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
// Representa o dto de resposta de item de pedido
public class OrderItemResponseDTO {
    // Atributos retornados na resposta
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}