package com.example.stockmarket.budget.messaging;

import com.example.stockmarket.budget.messaging.command.RegisterBudgetCommand;
import com.example.stockmarket.budget.messaging.event.BudgetRegisteredEvent;

public interface BudgetListener {
    void handle(RegisterBudgetCommand command);

    void on(BudgetRegisteredEvent event);
}
