package com.example.stockmarket.market.core;

import com.example.stockmarket.budget.messaging.event.ItemPurchaseFailedEvent;
import com.example.stockmarket.budget.messaging.event.ItemPurchaseScheduledEvent;
import com.example.stockmarket.budget.messaging.event.ItemSaleScheduledEvent;
import com.example.stockmarket.market.core.item.Item;
import com.example.stockmarket.market.core.item.ItemGenerator;
import com.example.stockmarket.market.core.item.ItemPriceChangeGenerator;
import com.example.stockmarket.market.messaging.ItemSaleFailedEvent;
import com.example.stockmarket.market.messaging.command.OpenMarketCommand;
import com.example.stockmarket.market.messaging.command.RegisterStockInMarketCommand;
import com.example.stockmarket.market.messaging.command.ScheduleItemSaleCommand;
import com.example.stockmarket.market.messaging.command.SchedulePurchaseCommand;
import com.example.stockmarket.market.messaging.event.*;
import com.example.stockmarket.messaging.core.MessageDispatcher;
import com.example.stockmarket.messaging.core.command.Command;
import com.example.stockmarket.messaging.core.event.Event;
import com.example.stockmarket.messaging.core.event.EventReadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class MarketSaga {

    private final MessageDispatcher messageDispatcher;
    private final EventReadService eventReadService;
    private final ItemPriceChangeGenerator itemPriceChangeGenerator;

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
        MarketAggregate marketAggregate = getMarketAggregate(command.getMarketId());

        StockItemRegisteredEvent event = StockItemRegisteredEvent.builder().itemName(command.getItemName())
                .itemQuantity(command.getItemQuantity()).itemPrice(command.getItemPrice()).marketId(command.getMarketId()).build();

        marketAggregate.apply(event);
        dispatchEvent(event);
    }

    public MarketProjection retrieveMarket(UUID id) {
        MarketAggregate marketAggregate = getMarketAggregate(id);
        return MarketProjection.builder().items(marketAggregate.getStock()).build();
    }

    private MarketAggregate getMarketAggregate(UUID id) {
        List<Event> events = eventReadService.retrieveAggregateEvent(id);
        MarketAggregate marketAggregate = new MarketAggregate();
        events.forEach(marketAggregate::apply);
        return marketAggregate;
    }

    public void initiatePurchase(UUID marketId, String itemName, int quantity) {
        log.info("Initiating a transaction on market {} of item {} with quantity {}", marketId, itemName, quantity);
        Optional<Item> item = getItemFromStock(marketId, itemName);

        item.ifPresentOrElse(i -> handleItemPurchase(marketId, itemName, quantity, i), () -> handleItemDoesNotExist(marketId, itemName));
    }

    private void handleItemDoesNotExist(UUID marketId, String itemName) {
        dispatchEvent(ItemPurchaseFailedEvent.builder().itemName(itemName).marketId(marketId).reason(String.format("Item %s does not exist in stock.", itemName)).build());
    }

    private void handleItemPurchase(UUID marketId, String itemName, int quantity, Item item) {
        if (item.getQuantity() < quantity) {
            dispatchEvent(ItemPurchaseFailedEvent.builder().itemName(itemName).marketId(marketId).reason("Not enough quantity in stock to buy").build());
        }

        dispatchCommand(SchedulePurchaseCommand.builder().marketId(marketId).itemName(itemName).quantity(quantity).itemPrice(item.getPrice()).build());
    }

    public void on(ItemPurchaseScheduledEvent event) {
        Optional<Item> itemFromStock = getItemFromStock(event.getMarketId(), event.getItemName());

        if (itemFromStock.isEmpty()) {
            dispatchEvent(ItemPurchaseFailedEvent.builder().itemName(event.getItemName()).marketId(event.getMarketId()).reason(String.format("Item %s does not exist in stock.", event.getItemName())).build());
            return;
        }

        dispatchEvent(ItemStockDecreasedEvent.builder().itemName(event.getItemName()).quantity(event.getQuantity()).marketId(event.getMarketId()).build());
        dispatchEvent(ItemPriceIncreaseEvent.builder().marketId(event.getMarketId()).itemName(event.getItemName())
                .increaseValue(itemPriceChangeGenerator.generatePriceChange(itemFromStock.get().getQuantity(), event.getQuantity(), event.getItemPrice())).build());
    }

    public void on(ItemPurchaseFailedEvent event) {
        log.error("Item {} purchase failed in market {} due to {}", event.getItemName(), event.getMarketId(), event.getReason());
    }

    public void initiateSale(UUID marketId, String itemName, int quantity) {
        Optional<Item> itemFromStock = getItemFromStock(marketId, itemName);

        if (itemFromStock.isPresent()) {
            dispatchCommand(ScheduleItemSaleCommand.builder().marketId(marketId).itemName(itemName).quantity(quantity).itemPrice(itemFromStock.get().getPrice()).build());
        } else {
            dispatchEvent(ItemSaleFailedEvent.builder().marketId(marketId).itemName(itemName).reason(String.format("Item %s does not exist in the market", itemName)).build());
        }
    }

    public void on(ItemSaleFailedEvent event) {
        log.error("Item {} sale failed in market {} due to {}", event.getItemName(), event.getMarketId(), event.getReason());
    }

    public void on(ItemSaleScheduledEvent event) {
        Optional<Item> itemFromStock = getItemFromStock(event.getMarketId(), event.getItemName());

        if (itemFromStock.isEmpty()) {
            dispatchEvent(ItemSaleFailedEvent.builder().itemName(event.getItemName()).marketId(event.getMarketId()).reason(String.format("Item %s does not exist in stock.", event.getItemName())).build());
            return;
        }

        dispatchEvent(ItemStockIncreasedEvent.builder().itemName(event.getItemName()).quantity(event.getQuantity()).marketId(event.getMarketId()).build());
        dispatchEvent(ItemPriceDecreaseEvent.builder().marketId(event.getMarketId()).itemName(event.getItemName())
                .increaseValue(itemPriceChangeGenerator.generatePriceChange(itemFromStock.get().getQuantity(), event.getQuantity(), event.getItemPrice())).build());

    }

    private Optional<Item> getItemFromStock(UUID marketId, String itemName) {
        MarketAggregate marketAggregate = getMarketAggregate(marketId);
        return marketAggregate.getStock().stream().filter(item -> item.getName().equals(itemName)).findAny();
    }

    private void dispatchCommand(Command command) {
        log.info("Sending command {}", command);
        messageDispatcher.dispatchToMarketQueue(command);
    }

    private void dispatchEvent(Event event) {
        log.info("Sending event {}", event);
        messageDispatcher.dispatchToMarketQueue(event);
    }
}
