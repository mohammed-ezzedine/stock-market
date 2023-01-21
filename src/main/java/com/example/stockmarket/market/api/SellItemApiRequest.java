package com.example.stockmarket.market.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SellItemApiRequest {
    private String itemName;
    private int quantity;
}
