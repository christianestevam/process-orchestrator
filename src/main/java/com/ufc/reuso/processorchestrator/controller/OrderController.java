package com.ufc.reuso.processorchestrator.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufc.reuso.processorchestrator.dto.InvoiceResponseDTO;
import com.ufc.reuso.processorchestrator.dto.OrderRequestDTO;
import com.ufc.reuso.processorchestrator.dto.OrderResponseDTO;
import com.ufc.reuso.processorchestrator.service.OrderService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Criar um novo pedido
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        OrderResponseDTO createdOrder = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(createdOrder);
    }

    // Buscar um pedido pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable UUID id) {
        OrderResponseDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    // Listar pedidos paginados
    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> getAllOrders(Pageable pageable) {
        Page<OrderResponseDTO> orders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }

    // Retornar o status do pedido
    @GetMapping("/{id}/status")
    public ResponseEntity<String> getOrderStatus(@PathVariable UUID id) {
        String status = orderService.getOrderStatus(id);
        return ResponseEntity.ok(status);
    }

    // Retornar o status do pagamento
    @GetMapping("/{id}/payment")
    public ResponseEntity<String> getPaymentStatus(@PathVariable UUID id) {
        String paymentStatus = orderService.getPaymentStatus(id);
        return ResponseEntity.ok(paymentStatus);
    }

    // Retornar detalhes da nota fiscal
    @GetMapping("/{id}/invoice")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceDetails(@PathVariable UUID id) {
        InvoiceResponseDTO invoice = orderService.getInvoiceDetails(id);
        return ResponseEntity.ok(invoice);
    }
}

