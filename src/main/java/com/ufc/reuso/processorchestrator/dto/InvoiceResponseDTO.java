package com.ufc.reuso.processorchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
// Representa o dto de resposta de fatura
public class InvoiceResponseDTO {
    // Atributos retornados na resposta
    private String invoiceNumber;
    private String invoicePdfUrl;
}