package com.example.stockmarket.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${application.messaging.exchange}")
    private String topicExchangeName;

    @Value("${application.messaging.queue.name}")
    private String queueName;

    @Value("${application.messaging.queue.routingKey}")
    private String routingKey;

    public String getTopicExchangeName() {
        return topicExchangeName;
    }

    public String getRoutingKey() {
        return routingKey;
    }


    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding marketBinding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
}
