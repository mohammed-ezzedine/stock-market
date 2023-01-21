package com.example.stockmarket.market.messaging.event;

import com.example.stockmarket.messaging.core.event.Event;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MarketOpenedEvent implements Event {
    private UUID marketId;

    @Override
    public UUID getAggregateId() {
        return marketId;
    }
}
