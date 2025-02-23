package com.ufc.reuso.processorchestrator.repository;

import com.ufc.reuso.processorchestrator.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    // Buscar uma nota fiscal pelo ID do pedido
    Optional<Invoice> findByOrderId(UUID orderId);
}
