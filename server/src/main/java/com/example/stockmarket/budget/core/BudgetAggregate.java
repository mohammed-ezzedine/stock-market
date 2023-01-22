package com.example.stockmarket.budget.core;

import com.example.stockmarket.budget.messaging.event.BudgetRegisteredEvent;
import com.example.stockmarket.budget.messaging.event.ItemPurchaseScheduledEvent;
import com.example.stockmarket.budget.messaging.event.ItemSaleScheduledEvent;
import com.example.stockmarket.messaging.core.event.Event;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        } else if (event instanceof ItemPurchaseScheduledEvent) {
            apply((ItemPurchaseScheduledEvent) event);
        } else if (event instanceof ItemSaleScheduledEvent) {
            apply((ItemSaleScheduledEvent) event);
        }
    }

    private void apply(BudgetRegisteredEvent event) {
        id = event.getBudgetId();
        marketId = event.getMarketId();
        amount = event.getAmount();
    }

    private void apply(ItemPurchaseScheduledEvent event) {
        amount -= event.getItemPrice() * event.getQuantity();
        getItemOwnership(event.getItemName()).ifPresentOrElse(item -> updateItemQuantity(event, item), () -> addItemToOwnership(event));
    }

    private void apply(ItemSaleScheduledEvent event) {
        amount += event.getQuantity() * event.getItemPrice();
        Optional<OwnedItem> itemOwnership = getItemOwnership(event.getItemName());
        itemOwnership.ifPresent(ownedItem -> ownedItem.setQuantity(ownedItem.getQuantity() - event.getQuantity()));
    }

    private void addItemToOwnership(ItemPurchaseScheduledEvent event) {
        items.add(OwnedItem.builder().name(event.getItemName()).quantity(event.getQuantity()).build());
    }

    private void updateItemQuantity(ItemPurchaseScheduledEvent event, OwnedItem item) {
        items.remove(item);
        items.add(OwnedItem.builder().name(item.getName()).quantity(item.getQuantity() + event.getQuantity()).build());
    }

    private Optional<OwnedItem> getItemOwnership(String itemName) {
        return items.stream().filter(i -> i.getName().equals(itemName)).findAny();
    }
}
