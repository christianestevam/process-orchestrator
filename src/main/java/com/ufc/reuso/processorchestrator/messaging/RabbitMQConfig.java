package com.ufc.reuso.processorchestrator.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // 🔹 EXCHANGE PRINCIPAL
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange("order.exchange", true, false);
    }

    // 🔹 FILAS DURÁVEIS (Criadas automaticamente)
    @Bean
    public Queue orderCreatedQueue() {
        return QueueBuilder.durable("order.created").build();
    }

    @Bean
    public Queue stockValidatedQueue() {
        return QueueBuilder.durable("order.stock.validated").build();
    }

    @Bean
    public Queue paymentProcessingQueue() {
        return QueueBuilder.durable("order.payment.processing").build();
    }

    @Bean
    public Queue paymentProcessedQueue() {
        return QueueBuilder.durable("order.payment.processed").build();
    }

    @Bean
    public Queue invoiceGenerationQueue() {
        return QueueBuilder.durable("order.invoice.generation").build();
    }

    @Bean
    public Queue invoiceGeneratedQueue() {
        return QueueBuilder.durable("order.invoice.generated").build();
    }

    // 🔹 BINDINGS - Ligação das filas ao exchange com routing keys
    @Bean
    public Binding orderCreatedBinding(Queue orderCreatedQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(orderCreatedQueue).to(orderExchange).with("order.created");
    }

    @Bean
    public Binding stockValidatedBinding(Queue stockValidatedQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(stockValidatedQueue).to(orderExchange).with("order.stock.validated");
    }

    @Bean
    public Binding paymentProcessingBinding(Queue paymentProcessingQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(paymentProcessingQueue).to(orderExchange).with("order.payment.processing");
    }

    @Bean
    public Binding paymentProcessedBinding(Queue paymentProcessedQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(paymentProcessedQueue).to(orderExchange).with("order.payment.processed");
    }

    @Bean
    public Binding invoiceGenerationBinding(Queue invoiceGenerationQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(invoiceGenerationQueue).to(orderExchange).with("order.invoice.generation");
    }

    @Bean
    public Binding invoiceGeneratedBinding(Queue invoiceGeneratedQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(invoiceGeneratedQueue).to(orderExchange).with("order.invoice.generated");
    }
}
