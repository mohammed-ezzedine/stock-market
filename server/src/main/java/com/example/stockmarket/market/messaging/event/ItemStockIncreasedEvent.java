package com.example.stockmarket.market.messaging.event;

import com.example.stockmarket.messaging.core.event.Event;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ItemStockIncreasedEvent implements Event {

    private UUID marketId;

    private String itemName;

    private int quantity;

    @Override
    public UUID getAggregateId() {
        return marketId;
    }
}
