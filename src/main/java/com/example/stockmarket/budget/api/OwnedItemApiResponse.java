package com.example.stockmarket.budget.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OwnedItemApiResponse {
    private String name;
    private int quantity;
}
