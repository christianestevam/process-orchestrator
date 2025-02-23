package com.ufc.reuso.processorchestrator.dto;

import com.ufc.reuso.processorchestrator.model.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
// Representa o dto de requisição de pedido
public class OrderRequestDTO {
    // Atributos recebidos na requisição
    private String customerName;
    private String customerEmail;
    private PaymentMethod paymentMethod;
    private List<OrderItemRequestDTO> items;

}