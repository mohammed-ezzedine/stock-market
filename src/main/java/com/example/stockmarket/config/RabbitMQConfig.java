package com.example.stockmarket.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${application.messaging.exchange}")
    private String topicExchangeName;

    @Value("${application.messaging.queues.market.name}")
    private String marketQueueName;

    @Value("${application.messaging.queues.market.routingKey}")
    private String marketRoutingKey;

    @Value("${application.messaging.queues.budget.name}")
    private String budgetQueueName;

    @Value("${application.messaging.queues.budget.routingKey}")
    private String budgetRoutingKey;

    public String getTopicExchangeName() {
        return topicExchangeName;
    }

    public String getMarketRoutingKey() {
        return marketRoutingKey;
    }

    public String getBudgetRoutingKey() {
        return budgetRoutingKey;
    }

    @Bean("marketQueue")
    Queue marketQueue() {
        return new Queue(marketQueueName, false);
    }


    @Bean("budgetQueue")
    Queue budgetQueue() {
        return new Queue(budgetQueueName, false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding marketBinding(@Qualifier("marketQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(marketRoutingKey);
    }

    @Bean
    Binding budgetBinding(@Qualifier("budgetQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(budgetRoutingKey);
    }
}
