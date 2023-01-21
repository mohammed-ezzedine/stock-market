package com.example.stockmarket.budget.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BudgetApiResponse {
    private double amount;
    private List<OwnedItemApiResponse> items;
}
