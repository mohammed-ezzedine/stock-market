package com.example.stockmarket.budget.messaging.command;

import com.example.stockmarket.messaging.core.command.Command;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RegisterBudgetCommand implements Command {
    private UUID marketId;
}
