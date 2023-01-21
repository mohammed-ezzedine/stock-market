package com.example.stockmarket.market.infra;

import com.example.stockmarket.messaging.core.event.EventWriteService;
import com.example.stockmarket.messaging.core.MessageDispatcher;
import com.example.stockmarket.market.core.MarketAggregate;
import com.example.stockmarket.market.core.MarketListener;
import com.example.stockmarket.market.messaging.command.OpenMarketCommand;
import com.example.stockmarket.market.messaging.event.MarketOpenedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RabbitListener(queues = "market")
@AllArgsConstructor
public class RabbitMQMarketListener implements MarketListener {
    private final MessageDispatcher messageDispatcher;
    private final EventWriteService eventWriteService;

    @Override
    @RabbitHandler
    public void handle(OpenMarketCommand command) {
        MarketAggregate.initialize(command);
        messageDispatcher.dispatch(MarketOpenedEvent.builder().marketId(command.getMarketId()).build());
    }

    @Override
    @RabbitHandler
    public void on(MarketOpenedEvent event) {
        eventWriteService.persist(event);
    }
}
