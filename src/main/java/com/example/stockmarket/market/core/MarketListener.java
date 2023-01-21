package com.example.stockmarket.market.core;

import com.example.stockmarket.market.messaging.command.OpenMarketCommand;
import com.example.stockmarket.market.messaging.event.MarketOpenedEvent;

public interface MarketListener {
    void handle(OpenMarketCommand command);

    void on(MarketOpenedEvent event);
}
