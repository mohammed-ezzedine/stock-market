package com.example.stockmarket.market.core;

import com.example.stockmarket.market.core.item.Item;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MarketProjection {
    private List<Item> items;
}
