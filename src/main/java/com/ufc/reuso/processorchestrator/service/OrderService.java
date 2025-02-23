package com.ufc.reuso.processorchestrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ufc.reuso.processorchestrator.dto.InvoiceResponseDTO;
import com.ufc.reuso.processorchestrator.dto.OrderRequestDTO;
import com.ufc.reuso.processorchestrator.dto.OrderResponseDTO;
import com.ufc.reuso.processorchestrator.model.Invoice;
import com.ufc.reuso.processorchestrator.model.Order;
import com.ufc.reuso.processorchestrator.model.OrderStatus;
import com.ufc.reuso.processorchestrator.model.Payment;
import com.ufc.reuso.processorchestrator.repository.InvoiceRepository;
import com.ufc.reuso.processorchestrator.repository.OrderRepository;
import com.ufc.reuso.processorchestrator.repository.PaymentRepository;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    // Criar um novo pedido
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequest) {
        // Lógica para criar um novo pedido
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        // Mapear dados do DTO para a entidade Order (faltando implementação detalhada)
        order = orderRepository.save(order);
        return new OrderResponseDTO(order);
    }

    // Buscar um pedido pelo ID
    public OrderResponseDTO getOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        return new OrderResponseDTO(order);
    }

    // Listar pedidos paginados
    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderResponseDTO::new);
    }

    // Retornar o status do pedido
    public String getOrderStatus(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        return order.getStatus().name();
    }

    // Retornar o status do pagamento
    public String getPaymentStatus(UUID id) {
        Payment payment = paymentRepository.findByOrderId(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        Order order = payment.getOrder();
        return order.getStatus().name();
    }

    // Retornar detalhes da nota fiscal
    public InvoiceResponseDTO getInvoiceDetails(UUID id) {
        Invoice invoice = invoiceRepository.findByOrderId(id)
                .orElseThrow(() -> new RuntimeException("Nota fiscal não encontrada"));
        return new InvoiceResponseDTO(invoice);
    }
} 

