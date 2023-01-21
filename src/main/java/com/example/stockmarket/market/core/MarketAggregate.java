package com.example.stockmarket.market.core;

import com.example.stockmarket.market.messaging.command.OpenMarketCommand;

import java.util.UUID;

public class MarketAggregate {

    private UUID id;

    private MarketAggregate(UUID id) {
        this.id = id;
    }

    public static MarketAggregate initialize(OpenMarketCommand command) {
        return new MarketAggregate(command.getMarketId());
    }
}
