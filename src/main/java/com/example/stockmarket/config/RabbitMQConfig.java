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

    @Value("${application.messaging.queues.market.name}")
    private String marketQueueName;

    @Value("${application.messaging.queues.market.routingKey}")
    private String marketRoutingKey;

    public String getTopicExchangeName() {
        return topicExchangeName;
    }

    public String getMarketRoutingKey() {
        return marketRoutingKey;
    }

    @Bean
    Queue marketQueue() {
        return new Queue(marketQueueName, false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(marketRoutingKey);
    }

//    @Bean
//    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
//        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
//        simpleMessageListenerContainer.setQueues(marketQueue());
//        simpleMessageListenerContainer.setMessageListener(new RabbitMQMarketListener());
//        return simpleMessageListenerContainer;
//    }
}
