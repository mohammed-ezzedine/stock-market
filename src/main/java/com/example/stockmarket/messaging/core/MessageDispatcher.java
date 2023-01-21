package com.example.stockmarket.messaging.core;

public interface MessageDispatcher {
    void dispatchToMarketQueue(Object message);
    void dispatchToBudgetQueue(Object message);
}
