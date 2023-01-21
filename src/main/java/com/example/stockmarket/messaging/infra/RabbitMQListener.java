package com.example.stockmarket.messaging.infra;

import com.example.stockmarket.budget.core.BudgetSaga;
import com.example.stockmarket.budget.messaging.BudgetListener;
import com.example.stockmarket.budget.messaging.command.RegisterBudgetCommand;
import com.example.stockmarket.budget.messaging.event.BudgetRegisteredEvent;
import com.example.stockmarket.budget.messaging.event.ItemPurchaseFailedEvent;
import com.example.stockmarket.budget.messaging.event.ItemPurchaseScheduledEvent;
import com.example.stockmarket.budget.messaging.event.ItemSaleScheduledEvent;
import com.example.stockmarket.market.core.MarketListener;
import com.example.stockmarket.market.core.MarketSaga;
import com.example.stockmarket.market.messaging.ItemSaleFailedEvent;
import com.example.stockmarket.market.messaging.command.OpenMarketCommand;
import com.example.stockmarket.market.messaging.command.RegisterStockInMarketCommand;
import com.example.stockmarket.market.messaging.command.ScheduleItemSaleCommand;
import com.example.stockmarket.market.messaging.command.SchedulePurchaseCommand;
import com.example.stockmarket.market.messaging.event.*;
import com.example.stockmarket.messaging.core.event.EventWriteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RabbitListener(queues = "#{'${application.messaging.queue.name}'}")
@AllArgsConstructor
public class RabbitMQListener implements MarketListener, BudgetListener {
    private final EventWriteService eventWriteService;
    private final MarketSaga marketSaga;
    private final BudgetSaga budgetSaga;

    @Override
    @RabbitHandler
    public void handle(OpenMarketCommand command) {
        marketSaga.handle(command);
    }

    @Override
    @RabbitHandler
    public void handle(RegisterStockInMarketCommand command) {
        marketSaga.handle(command);
    }

    @Override
    @RabbitHandler
    public void handle(RegisterBudgetCommand command) {
        budgetSaga.handle(command);
    }

    @Override
    @RabbitHandler
    public void handle(SchedulePurchaseCommand command) {
        budgetSaga.handle(command);
    }

    @Override
    @RabbitHandler
    public void handle(ScheduleItemSaleCommand command) {
        budgetSaga.handle(command);
    }

    @Override
    @RabbitHandler
    public void on(MarketOpenedEvent event) {
        eventWriteService.persist(event);
        marketSaga.on(event);
        budgetSaga.on(event);
    }

    @Override
    @RabbitHandler
    public void on(StockItemRegisteredEvent event) {
        eventWriteService.persist(event);
    }

    @Override
    @RabbitHandler
    public void on(ItemPurchaseScheduledEvent event) {
        eventWriteService.persist(event);
        marketSaga.on(event);
    }

    @Override
    @RabbitHandler
    public void on(ItemPurchaseFailedEvent event) {
        eventWriteService.persist(event);
        marketSaga.on(event);
    }

    @Override
    @RabbitHandler
    public void on(ItemStockDecreasedEvent event) {
        eventWriteService.persist(event);
    }

    @Override
    @RabbitHandler
    public void on(ItemPriceIncreaseEvent event) {
        eventWriteService.persist(event);
    }

    @Override
    @RabbitHandler
    public void on(ItemSaleFailedEvent event) {
        eventWriteService.persist(event);
        marketSaga.on(event);
    }

    @Override
    @RabbitHandler
    public void on(ItemSaleScheduledEvent event) {
        eventWriteService.persist(event);
        marketSaga.on(event);
    }

    @Override
    @RabbitHandler
    public void on(ItemStockIncreasedEvent event) {
        eventWriteService.persist(event);
    }

    @Override
    @RabbitHandler
    public void on(ItemPriceDecreaseEvent event) {
        eventWriteService.persist(event);
    }

    @Override
    @RabbitHandler
    public void on(BudgetRegisteredEvent event) {
        eventWriteService.persist(event);
    }
}
