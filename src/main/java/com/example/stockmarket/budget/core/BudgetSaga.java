package com.example.stockmarket.budget.core;

import com.example.stockmarket.aggregate.core.AggregateLinkReadService;
import com.example.stockmarket.aggregate.core.AggregateLinkWriteService;
import com.example.stockmarket.budget.messaging.command.RegisterBudgetCommand;
import com.example.stockmarket.budget.messaging.event.BudgetRegisteredEvent;
import com.example.stockmarket.market.messaging.event.MarketOpenedEvent;
import com.example.stockmarket.messaging.core.MessageDispatcher;
import com.example.stockmarket.messaging.core.command.Command;
import com.example.stockmarket.messaging.core.event.Event;
import com.example.stockmarket.messaging.core.event.EventReadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class BudgetSaga {

    private final MessageDispatcher messageDispatcher;
    private final BudgetGenerator budgetGenerator;
    private final EventReadService eventReadService;
    private final AggregateLinkWriteService aggregateLinkWriteService;
    private final AggregateLinkReadService aggregateLinkReadService;

    public BudgetProjection getBudgetByMarketId(UUID marketId) {
        UUID budgetAggregateId = aggregateLinkReadService.getLinkedAggregate(marketId, BudgetAggregate.class.getName());
        log.info("Retrieving budget of id {} for market {}", budgetAggregateId, marketId);

        List<Event> events = eventReadService.retrieveAggregateEvent(budgetAggregateId);

        BudgetAggregate budgetAggregate = new BudgetAggregate();
        events.forEach(budgetAggregate::apply);

        return BudgetProjection.builder().budgetId(budgetAggregate.getId()).amount(budgetAggregate.getAmount()).items(budgetAggregate.getItems()).build();
    }

    public void on(MarketOpenedEvent event) {
        log.info("handling event {}", event);
        dispatchCommand(RegisterBudgetCommand.builder().marketId(event.getMarketId()).build());
    }

    public void handle(RegisterBudgetCommand command) {
        log.info("handling command {}", command);
        UUID budgetId = UUID.randomUUID();
        dispatchEvent(BudgetRegisteredEvent.builder().marketId(command.getMarketId()).amount(budgetGenerator.generate()).budgetId(budgetId).build());
        aggregateLinkWriteService.linkAggregates(command.getMarketId(), budgetId, BudgetAggregate.class.getName());
    }

    private void dispatchEvent(Event event) {
        log.info("Sending event {}", event);
        messageDispatcher.dispatchToBudgetQueue(event);
    }

    private void dispatchCommand(Command command) {
        log.info("Sending command {}", command);
        messageDispatcher.dispatchToBudgetQueue(command);
    }
}
