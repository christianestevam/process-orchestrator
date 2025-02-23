package com.ufc.reuso.processorchestrator.dto;

import com.ufc.reuso.processorchestrator.model.Payment;
import com.ufc.reuso.processorchestrator.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Representa o dto de resposta de pagamento
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PaymentResponseDTO {
    // Atributos retornados na resposta
    private PaymentMethod paymentMethod;
    private boolean success;
    private String transactionId;

    // Construtor que recebe um objeto Payment
    public PaymentResponseDTO(Payment payment) {
        this.paymentMethod = payment.getPaymentMethod();
        this.success = payment.isSuccess();
        this.transactionId = payment.getTransactionId();
    }
}