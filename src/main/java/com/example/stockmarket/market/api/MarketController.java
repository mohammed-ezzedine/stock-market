package com.example.stockmarket.market.api;

import com.example.stockmarket.market.core.MarketSaga;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@AllArgsConstructor
@RestController
public class MarketController {
    private final MarketSaga marketSaga;

    @PostMapping("init")
    public ResponseEntity<MarketOpeningResponse> openMarket() {
        UUID marketId = marketSaga.dispatchOpenMarketCommand();
        return ResponseEntity.ok(MarketOpeningResponse.builder().marketId(marketId).build());
    }
}
