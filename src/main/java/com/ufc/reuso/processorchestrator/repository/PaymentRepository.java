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

    // Métodos personalizados podem ser adicionados aqui

    // Exemplo: Buscar pagamentos por ID do pedido
    Optional<Payment> findByOrderId(UUID orderId);

    // Exemplo: Buscar pagamentos por método de pagamento
    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);

    // Exemplo: Buscar pagamentos bem-sucedidos
    List<Payment> findBySuccessTrue();

    // Exemplo: Buscar pagamentos realizados após uma determinada data
    List<Payment> findByPaymentDateAfter(LocalDateTime paymentDate);

    // Exemplo: Buscar pagamentos por ID da transação
    Optional<Payment> findByTransactionId(String transactionId);
}