package com.ufc.reuso.processorchestrator.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishEvent(String routingKey, Object event) {
        rabbitTemplate.convertAndSend("order.exchange", routingKey, event);
    }
}
