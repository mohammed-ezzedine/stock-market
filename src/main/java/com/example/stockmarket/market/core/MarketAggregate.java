package com.example.stockmarket.market.core;

import com.example.stockmarket.market.core.item.Item;
import com.example.stockmarket.market.messaging.event.ItemPriceIncreaseEvent;
import com.example.stockmarket.market.messaging.event.ItemStockDecreasedEvent;
import com.example.stockmarket.market.messaging.event.MarketOpenedEvent;
import com.example.stockmarket.market.messaging.event.StockItemRegisteredEvent;
import com.example.stockmarket.messaging.core.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MarketAggregate {

    private UUID id;

    private List<Item> stock;

    public UUID getId() {
        return id;
    }

    public List<Item> getStock() {
        return stock;
    }

    public MarketAggregate() {
        this.stock = new ArrayList<>();
    }

    public void apply(Event event) {
        if (event instanceof StockItemRegisteredEvent) {
            apply((StockItemRegisteredEvent) event);
        } else if (event instanceof MarketOpenedEvent) {
            apply((MarketOpenedEvent) event);
        } else if (event instanceof ItemStockDecreasedEvent) {
            apply((ItemStockDecreasedEvent) event);
        } else if (event instanceof ItemPriceIncreaseEvent) {
            apply((ItemPriceIncreaseEvent) event);
        }
    }

    private void apply(MarketOpenedEvent event) {
        id = event.getMarketId();
    }

    private void apply(StockItemRegisteredEvent event) {
        if (stock.stream().anyMatch(s -> s.getName().equals(event.getItemName()))) {
            throw new IllegalStateException("Item already registered in stock.");
        }

        stock.add(Item.builder().name(event.getItemName()).quantity(event.getItemQuantity()).price(event.getItemPrice()).build());
    }

    private void apply(ItemStockDecreasedEvent event) {
        Item item = stock.stream().filter(i -> i.getName().equals(event.getItemName())).findAny()
                .orElseThrow(() -> new IllegalStateException(String.format("Item %s did not exist in stock", event.getItemName())));

        item.setQuantity(item.getQuantity() - event.getQuantity());
    }

    private void apply(ItemPriceIncreaseEvent event) {
        Item item = stock.stream().filter(i -> i.getName().equals(event.getItemName())).findAny()
                .orElseThrow(() -> new IllegalStateException(String.format("Item %s did not exist in stock", event.getItemName())));

        item.setPrice(item.getPrice() + event.getIncreaseValue());
    }
}
