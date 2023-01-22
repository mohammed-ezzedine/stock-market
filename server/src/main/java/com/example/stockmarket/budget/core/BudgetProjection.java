package com.example.stockmarket.budget.core;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class BudgetProjection {
    private UUID budgetId;
    private double amount;
    private List<OwnedItem> items;
}
