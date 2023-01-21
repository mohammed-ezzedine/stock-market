package com.example.stockmarket.market.core;

import com.example.stockmarket.budget.messaging.event.ItemPurchaseFailedEvent;
import com.example.stockmarket.budget.messaging.event.ItemPurchaseScheduledEvent;
import com.example.stockmarket.market.messaging.command.OpenMarketCommand;
import com.example.stockmarket.market.messaging.command.RegisterStockInMarketCommand;
import com.example.stockmarket.market.messaging.event.ItemPriceIncreaseEvent;
import com.example.stockmarket.market.messaging.event.ItemStockDecreasedEvent;
import com.example.stockmarket.market.messaging.event.MarketOpenedEvent;
import com.example.stockmarket.market.messaging.event.StockItemRegisteredEvent;

public interface MarketListener {
    void handle(OpenMarketCommand command);
    void handle(RegisterStockInMarketCommand command);

    void on(MarketOpenedEvent event);
    void on(StockItemRegisteredEvent event);
    void on(ItemPurchaseScheduledEvent event);
    void on(ItemPurchaseFailedEvent event);
    void on(ItemStockDecreasedEvent event);
    void on(ItemPriceIncreaseEvent event);
}
