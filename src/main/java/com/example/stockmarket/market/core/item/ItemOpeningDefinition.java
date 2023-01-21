package com.example.stockmarket.market.core.item;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemOpeningDefinition {
    private String name;
    private double priceLowerBound;
    private double priceHigherBound;
}
