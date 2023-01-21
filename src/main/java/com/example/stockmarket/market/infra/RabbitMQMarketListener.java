package com.example.stockmarket.market.infra;

import com.example.stockmarket.market.core.MarketListener;
import com.example.stockmarket.market.core.MarketSaga;
import com.example.stockmarket.market.messaging.command.OpenMarketCommand;
import com.example.stockmarket.market.messaging.command.RegisterStockInMarketCommand;
import com.example.stockmarket.market.messaging.event.MarketOpenedEvent;
import com.example.stockmarket.market.messaging.event.StockItemRegisteredEvent;
import com.example.stockmarket.messaging.core.event.EventWriteService;
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
    private final EventWriteService eventWriteService;
    private final MarketSaga marketSaga;

    @Override
    @RabbitHandler
    public void handle(OpenMarketCommand command) {
        marketSaga.handle(command);
    }

    @Override
    @RabbitHandler
    public void handle(RegisterStockInMarketCommand command) {
        marketSaga.handle(command);
    }

    @Override
    @RabbitHandler
    public void on(MarketOpenedEvent event) {
        eventWriteService.persist(event);
        marketSaga.on(event);
    }

    @Override
    @RabbitHandler
    public void on(StockItemRegisteredEvent event) {
        eventWriteService.persist(event);
    }
}
