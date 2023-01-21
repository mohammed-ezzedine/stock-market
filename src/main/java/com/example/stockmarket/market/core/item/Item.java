package com.example.stockmarket.market.core.item;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private String name;
    private int quantity;
    private double price;
}
