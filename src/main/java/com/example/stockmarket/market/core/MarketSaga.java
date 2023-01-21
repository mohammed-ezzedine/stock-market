package com.example.stockmarket.market.core;

import com.example.stockmarket.market.core.item.ItemGenerator;
import com.example.stockmarket.market.messaging.command.OpenMarketCommand;
import com.example.stockmarket.market.messaging.command.RegisterStockInMarketCommand;
import com.example.stockmarket.market.messaging.event.MarketOpenedEvent;
import com.example.stockmarket.market.messaging.event.StockItemRegisteredEvent;
import com.example.stockmarket.messaging.core.MessageDispatcher;
import com.example.stockmarket.messaging.core.command.Command;
import com.example.stockmarket.messaging.core.event.Event;
import com.example.stockmarket.messaging.core.event.EventReadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class MarketSaga {

    private final MessageDispatcher messageDispatcher;
    private final EventReadService eventReadService;

    public UUID dispatchOpenMarketCommand() {
        UUID marketId = UUID.randomUUID();
        dispatchCommand(OpenMarketCommand.builder().marketId(marketId).build());
        return marketId;
    }

    public void handle(OpenMarketCommand command) {
        log.info("handling command {}", command);
        dispatchEvent(MarketOpenedEvent.builder().marketId(command.getMarketId()).build());
    }

    public void on(MarketOpenedEvent event) {
        log.info("handling event {}", event);
        ItemGenerator.generate().forEach(item -> {
            RegisterStockInMarketCommand command = RegisterStockInMarketCommand.builder().marketId(event.getMarketId()).itemName(item.getName()).itemQuantity(item.getQuantity()).itemPrice(item.getPrice()).build();
            dispatchCommand(command);
        });
    }

    public void handle(RegisterStockInMarketCommand command) {
        MarketAggregate marketAggregate = getMarketAggregate(command);

        StockItemRegisteredEvent event = StockItemRegisteredEvent.builder().itemName(command.getItemName())
                .itemQuantity(command.getItemQuantity()).itemPrice(command.getItemPrice()).marketId(command.getMarketId()).build();

        marketAggregate.apply(event);
        dispatchEvent(event);
    }

    private MarketAggregate getMarketAggregate(RegisterStockInMarketCommand command) {
        List<Event> events = eventReadService.retrieveAggregateEvent(command.getMarketId());
        MarketAggregate marketAggregate = new MarketAggregate();
        events.forEach(marketAggregate::apply);
        return marketAggregate;
    }

    private void dispatchCommand(Command command) {
        log.info("Sending command {}", command);
        messageDispatcher.dispatch(command);
    }

    private void dispatchEvent(Event event) {
        log.info("Sending event {}", event);
        messageDispatcher.dispatch(event);
    }

    public MarketProjection retrieveMarket(UUID id) {
        List<Event> events = eventReadService.retrieveAggregateEvent(id);
        MarketAggregate marketAggregate = new MarketAggregate();
        events.forEach(marketAggregate::apply);
        return MarketProjection.builder().items(marketAggregate.getStock()).build();
    }
}
