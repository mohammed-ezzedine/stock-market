package com.example.stockmarket.messaging.infra;

import com.example.stockmarket.config.RabbitMQConfig;
import com.example.stockmarket.messaging.core.MessageDispatcher;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RabbitMQMessageDispatcher implements MessageDispatcher {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig config;

    @Override
    public void dispatch(Object message) {
        rabbitTemplate.convertAndSend(config.getTopicExchangeName(), config.getMarketRoutingKey(), message);
    }
}
