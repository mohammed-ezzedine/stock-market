package com.example.stockmarket.market.api;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MarketOpeningResponse {
    private UUID marketId;
}
