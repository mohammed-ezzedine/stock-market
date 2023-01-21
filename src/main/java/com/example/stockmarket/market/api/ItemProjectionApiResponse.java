package com.example.stockmarket.market.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemProjectionApiResponse {
    private String name;
    private int quantity;
    private double price;
}
