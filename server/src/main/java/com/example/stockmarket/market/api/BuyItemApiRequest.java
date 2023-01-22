package com.example.stockmarket.market.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyItemApiRequest {
    private String itemName;
    private int quantity;
}
