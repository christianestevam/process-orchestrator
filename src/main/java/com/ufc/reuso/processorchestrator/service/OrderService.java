package com.ufc.reuso.processorchestrator.service;

import com.ufc.reuso.processorchestrator.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ufc.reuso.processorchestrator.dto.InvoiceResponseDTO;
import com.ufc.reuso.processorchestrator.dto.OrderRequestDTO;
import com.ufc.reuso.processorchestrator.dto.OrderResponseDTO;
import com.ufc.reuso.processorchestrator.repository.InvoiceRepository;
import com.ufc.reuso.processorchestrator.repository.OrderRepository;
import com.ufc.reuso.processorchestrator.repository.PaymentRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        order.setCustomerName(orderRequest.getCustomerName());
        order.setCustomerEmail(orderRequest.getCustomerEmail());

        // Mapear os itens do pedido
        Order finalOrder = order;
        List<OrderItem> items = orderRequest.getItems().stream()
                .map(itemRequest -> {
                    OrderItem item = new OrderItem();
                    item.setProductName(itemRequest.getProductName());
                    item.setQuantity(itemRequest.getQuantity());
                    item.setUnitPrice(itemRequest.getUnitPrice());
                    item.setOrder(finalOrder); // Associe o item ao pedido
                    return item;
                })
                .collect(Collectors.toList());

        order.setItems(items);

        // Crie e associe o pagamento (se necessário)
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(orderRequest.getPaymentMethod());
        payment.setSuccess(true); // Defina o status do pagamento
        payment.setTransactionId("txn_123456"); // Defina o ID da transação
        order.setPayment(payment);

        // Crie e associe a nota fiscal (se necessário)
        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setInvoiceNumber("INV-2023-001"); // Defina o número da nota fiscal
        invoice.setInvoicePdfUrl("http://example.com/invoices/INV-2023-001.pdf"); // Defina a URL do PDF
        order.setInvoice(invoice);


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

