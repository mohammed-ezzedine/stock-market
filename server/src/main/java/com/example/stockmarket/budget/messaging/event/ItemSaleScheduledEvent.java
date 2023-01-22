package com.example.stockmarket.budget.messaging.event;

import com.example.stockmarket.messaging.core.event.Event;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ItemSaleScheduledEvent implements Event {
    private UUID budgetId;
    private UUID marketId;
    private String itemName;
    private int quantity;
    private double itemPrice;

    @Override
    public UUID getAggregateId() {
        return budgetId;
    }
}
