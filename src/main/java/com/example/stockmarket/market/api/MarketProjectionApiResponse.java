package com.example.stockmarket.market.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MarketProjectionApiResponse {
    private List<ItemProjectionApiResponse> items;
}
