package com.ufc.reuso.processorchestrator.dto;

import com.ufc.reuso.processorchestrator.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// Representa o dto de resposta de pagamento
@Getter @Setter
@AllArgsConstructor
public class PaymentResponseDTO {
    // Atributos retornados na resposta
    private PaymentMethod paymentMethod;
    private boolean success;
    private String transactionId;
}