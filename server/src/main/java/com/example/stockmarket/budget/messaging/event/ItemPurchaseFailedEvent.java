package com.example.stockmarket.budget.messaging.event;

import com.example.stockmarket.messaging.core.event.Event;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ItemPurchaseFailedEvent implements Event {

    private UUID marketId;
    private String itemName;
    private String reason;

    @Override
    public UUID getAggregateId() {
        return marketId;
    }
}
