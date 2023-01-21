package com.example.stockmarket.budget.core;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OwnedItem {
    private String name;
    private int quantity;
}
