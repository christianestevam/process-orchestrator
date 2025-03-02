package com.ufc.reuso.processorchestrator.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufc.reuso.processorchestrator.dto.InvoiceResponseDTO;
import com.ufc.reuso.processorchestrator.dto.OrderRequestDTO;
import com.ufc.reuso.processorchestrator.dto.OrderResponseDTO;
import com.ufc.reuso.processorchestrator.dto.PaymentResponseDTO;
import com.ufc.reuso.processorchestrator.orchestrators.ProcessOrchestrator;
import com.ufc.reuso.processorchestrator.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProcessOrchestrator processOrchestrator;

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
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
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
        return status != null ? ResponseEntity.ok(status) : ResponseEntity.notFound().build();
    }

    // Retornar detalhes do pagamento
    @GetMapping("/{id}/payment")
    public ResponseEntity<PaymentResponseDTO> getPaymentStatus(@PathVariable UUID id) {
        PaymentResponseDTO payment = orderService.getPaymentStatus(id);
        return payment != null ? ResponseEntity.ok(payment) : ResponseEntity.notFound().build();
    }

    // Retornar detalhes da nota fiscal
    @GetMapping("/{id}/invoice")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceDetails(@PathVariable UUID id) {
        InvoiceResponseDTO invoice = orderService.getInvoiceDetails(id);
        return invoice != null ? ResponseEntity.ok(invoice) : ResponseEntity.notFound().build();
    }

    // **Novo Endpoint**: Iniciar o processamento do pedido
    @PostMapping("/{id}/process")
    public ResponseEntity<String> processOrder(@PathVariable UUID id) {
        try {
            processOrchestrator.startOrderProcessing(id);
            return ResponseEntity.ok("Pedido em processamento.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
