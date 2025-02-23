package com.ufc.reuso.processorchestrator.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
// Representa o dto de requisição de item de pedido
public class OrderItemRequestDTO {
    // Atributos recebidos na requisição
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
}