package com.ufc.reuso.processorchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
// Representa o dto de requisição de item de pedido
public class OrderItemRequestDTO {
    // Atributos recebidos na requisição
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
}