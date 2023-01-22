package com.example.stockmarket.market.messaging;

import com.example.stockmarket.messaging.core.event.Event;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ItemSaleFailedEvent implements Event {

    private UUID marketId;
    private String itemName;
    private String reason;

    @Override
    public UUID getAggregateId() {
        return marketId;
    }
}
