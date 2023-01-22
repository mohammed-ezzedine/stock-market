package com.example.stockmarket.messaging.core.event;

import java.util.List;
import java.util.UUID;

public interface EventStorageManager {
    void persist(Event event);
    List<Event> retrieveAggregateEvent(UUID aggregateId);
}
