package com.example.stockmarket.budget.core;

import com.example.stockmarket.budget.messaging.event.BudgetRegisteredEvent;
import com.example.stockmarket.messaging.core.event.Event;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class BudgetAggregate {

    private UUID marketId;

    private UUID id;

    private double amount;

    private List<OwnedItem> items = new ArrayList<>();

    public void apply(Event event) {
        if (event instanceof BudgetRegisteredEvent) {
            apply((BudgetRegisteredEvent) event);
        }
    }

    private void apply(BudgetRegisteredEvent event) {
        id = event.getBudgetId();
        marketId = event.getMarketId();
        amount = event.getAmount();
    }
}
