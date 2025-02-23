package com.ufc.reuso.processorchestrator.dto;

import com.ufc.reuso.processorchestrator.model.Invoice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
// Representa o dto de resposta de fatura
public class InvoiceResponseDTO {
    // Atributos retornados na resposta
    private String invoiceNumber;
    private String invoicePdfUrl;

    // Construtor que recebe um objeto Invoice
    public InvoiceResponseDTO(Invoice invoice) {
        this.invoiceNumber = invoice.getInvoiceNumber();
        this.invoicePdfUrl = invoice.getInvoicePdfUrl();
    }

}