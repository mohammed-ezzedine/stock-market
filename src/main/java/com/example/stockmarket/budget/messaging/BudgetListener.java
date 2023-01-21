package com.example.stockmarket.budget.messaging;

import com.example.stockmarket.budget.messaging.command.RegisterBudgetCommand;
import com.example.stockmarket.budget.messaging.event.BudgetRegisteredEvent;
import com.example.stockmarket.market.messaging.command.SchedulePurchaseCommand;

public interface BudgetListener {
    void handle(RegisterBudgetCommand command);
    void handle(SchedulePurchaseCommand command);

    void on(BudgetRegisteredEvent event);
}
