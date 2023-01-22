package com.example.stockmarket.budget.api;

import com.example.stockmarket.budget.core.BudgetProjection;
import com.example.stockmarket.budget.core.BudgetSaga;
import com.example.stockmarket.budget.core.OwnedItem;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("budget")
@CrossOrigin
public class BudgetController {

    private final BudgetSaga budgetSaga;

    @GetMapping("{marketId}")
    public ResponseEntity<BudgetApiResponse> get(@PathVariable UUID marketId) {
        log.info("Received a request to get the budget for market {}", marketId);
        BudgetProjection projection = budgetSaga.getBudgetByMarketId(marketId);
        return ResponseEntity.ok(mapBudget(projection));
    }

    private static BudgetApiResponse mapBudget(BudgetProjection projection) {
        return BudgetApiResponse.builder().amount(projection.getAmount()).items(projection.getItems().stream().map(BudgetController::mapItem).collect(Collectors.toList())).build();
    }

    private static OwnedItemApiResponse mapItem(OwnedItem i) {
        return OwnedItemApiResponse.builder().name(i.getName()).quantity(i.getQuantity()).build();
    }
}
