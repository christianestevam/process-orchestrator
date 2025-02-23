package com.ufc.reuso.processorchestrator.repository;

import com.ufc.reuso.processorchestrator.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    // Métodos personalizados podem ser adicionados aqui

    // Exemplo: Buscar itens de pedido por ID do pedido
    List<OrderItem> findByOrderId(UUID orderId);

    // Exemplo: Buscar itens de pedido por nome do produto (ignorando maiúsculas e minúsculas)
    List<OrderItem> findByProductNameIgnoreCase(String productName);

    // Exemplo: Buscar itens de pedido com quantidade maior que um valor específico
    List<OrderItem> findByQuantityGreaterThan(Integer quantity);

    // Exemplo: Buscar itens de pedido com preço unitário maior que um valor específico
    List<OrderItem> findByUnitPriceGreaterThan(BigDecimal unitPrice);
}