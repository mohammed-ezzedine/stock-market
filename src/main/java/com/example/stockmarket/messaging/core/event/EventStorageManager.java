package com.example.stockmarket.messaging.core.event;

public interface EventStorageManager {
    void persist(Event event);
}
