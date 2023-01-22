package com.example.stockmarket.messaging.core.event;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EventReadService {
    private final EventStorageManager storageManager;

    public List<Event> retrieveAggregateEvent(UUID aggregateId) {
        return storageManager.retrieveAggregateEvent(aggregateId);
    }
}
