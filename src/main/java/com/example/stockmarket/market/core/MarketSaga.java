package com.example.stockmarket.market.core;

import com.example.stockmarket.market.messaging.command.OpenMarketCommand;
import com.example.stockmarket.messaging.core.MessageDispatcher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MarketSaga {

    private final MessageDispatcher messageDispatcher;

    public UUID dispatchOpenMarketCommand() {
        UUID marketId = UUID.randomUUID();
        messageDispatcher.dispatch(OpenMarketCommand.builder().marketId(marketId).build());
        return marketId;
    }
}
