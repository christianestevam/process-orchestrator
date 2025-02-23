package com.ufc.reuso.processorchestrator.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue stockValidationQueue() {
        return new Queue("stock-validation-queue");
    }

    @Bean
    public Queue paymentProcessingQueue() {
        return new Queue("payment-processing-queue");
    }

    @Bean
    public Queue invoiceGenerationQueue() {
        return new Queue("invoice-generation-queue");
    }
}

