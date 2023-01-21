package com.example.stockmarket.market.api;

import com.example.stockmarket.market.core.MarketProjection;
import com.example.stockmarket.market.core.MarketSaga;
import com.example.stockmarket.market.core.item.Item;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("market")
public class MarketController {
    private final MarketSaga marketSaga;

    @PostMapping("init")
    public ResponseEntity<MarketOpeningResponse> openMarket() {
        log.info("Received a request to open market");
        UUID marketId = marketSaga.dispatchOpenMarketCommand();
        return ResponseEntity.ok(MarketOpeningResponse.builder().marketId(marketId).build());
    }

    @GetMapping("{id}")
    public ResponseEntity<MarketProjectionApiResponse> getMarket(@PathVariable UUID id) {
        MarketProjection marketProjection = marketSaga.retrieveMarket(id);
        return ResponseEntity.ok(mapMarket(marketProjection));
    }

    @PostMapping("{marketId}/buy/")
    public ResponseEntity<Void> buyItem(@PathVariable UUID marketId, @RequestBody BuyItemApiRequest request) {
        marketSaga.initiatePurchase(marketId, request.getItemName(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

    @PostMapping("{marketId}/sell/")
    public ResponseEntity<Void> sellItem(@PathVariable UUID marketId, @RequestBody SellItemApiRequest request) {
        marketSaga.initiateSale(marketId, request.getItemName(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

    private static MarketProjectionApiResponse mapMarket(MarketProjection marketProjection) {
        return MarketProjectionApiResponse.builder()
                .items(marketProjection.getItems().stream().map(MarketController::mapItem).collect(Collectors.toList())).build();
    }

    private static ItemProjectionApiResponse mapItem(Item i) {
        return ItemProjectionApiResponse.builder().name(i.getName()).price(i.getPrice()).quantity(i.getQuantity()).build();
    }
}
