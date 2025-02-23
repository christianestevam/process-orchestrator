package com.ufc.reuso.processorchestrator.repository;

import com.ufc.reuso.processorchestrator.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    // Métodos personalizados podem ser adicionados aqui, se necessário
}