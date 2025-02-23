package com.ufc.reuso.processorchestrator.repository;

import com.ufc.reuso.processorchestrator.model.Order;
import com.ufc.reuso.processorchestrator.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    // Métodos personalizados podem ser adicionados aqui

    // Exemplo: Buscar pedidos por status
    List<Order> findByStatus(OrderStatus status);

    // Exemplo: Buscar pedidos criados após uma determinada data
    List<Order> findByCreatedAtAfter(LocalDateTime date);

    // Exemplo: Buscar pedidos por e-mail do cliente
    List<Order> findByCustomerEmail(String customerEmail);

    // Exemplo: Buscar pedidos por nome do cliente (ignorando maiúsculas e minúsculas)
    List<Order> findByCustomerNameIgnoreCase(String customerName);
}