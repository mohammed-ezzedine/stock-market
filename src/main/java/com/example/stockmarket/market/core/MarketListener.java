package com.example.stockmarket.market.core;

import com.example.stockmarket.budget.messaging.event.ItemPurchaseFailedEvent;
import com.example.stockmarket.budget.messaging.event.ItemPurchaseScheduledEvent;
import com.example.stockmarket.budget.messaging.event.ItemSaleScheduledEvent;
import com.example.stockmarket.market.messaging.ItemSaleFailedEvent;
import com.example.stockmarket.market.messaging.command.OpenMarketCommand;
import com.example.stockmarket.market.messaging.command.RegisterStockInMarketCommand;
import com.example.stockmarket.market.messaging.event.*;

public interface MarketListener {
    void handle(OpenMarketCommand command);
    void handle(RegisterStockInMarketCommand command);

    void on(MarketOpenedEvent event);
    void on(StockItemRegisteredEvent event);
    void on(ItemPurchaseScheduledEvent event);
    void on(ItemPurchaseFailedEvent event);
    void on(ItemStockDecreasedEvent event);
    void on(ItemPriceIncreaseEvent event);
    void on(ItemSaleFailedEvent event);
    void on(ItemSaleScheduledEvent event);
    void on(ItemStockIncreasedEvent event);
    void on(ItemPriceDecreaseEvent event);
}
