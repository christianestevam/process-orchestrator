package com.ufc.reuso.processorchestrator.repository;

import com.ufc.reuso.processorchestrator.model.Payment;
import com.ufc.reuso.processorchestrator.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    // Buscar pagamentos por ID do pedido
    Optional<Payment> findByOrderId(UUID orderId);

    // Buscar pagamentos por método de pagamento
    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);

    // Buscar pagamentos bem-sucedidos
    List<Payment> findBySuccessTrue();

    // Buscar pagamentos realizados após uma determinada data
    List<Payment> findByPaymentDateAfter(LocalDateTime paymentDate);

    // Buscar pagamentos por ID da transação
    Optional<Payment> findByTransactionId(String transactionId);
}