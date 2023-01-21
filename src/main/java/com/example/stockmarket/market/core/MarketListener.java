package com.example.stockmarket.market.core;

import com.example.stockmarket.market.messaging.command.OpenMarketCommand;
import com.example.stockmarket.market.messaging.command.RegisterStockInMarketCommand;
import com.example.stockmarket.market.messaging.event.MarketOpenedEvent;
import com.example.stockmarket.market.messaging.event.StockItemRegisteredEvent;

public interface MarketListener {
    void handle(OpenMarketCommand command);
    void handle(RegisterStockInMarketCommand command);

    void on(MarketOpenedEvent event);
    void on(StockItemRegisteredEvent event);
}
