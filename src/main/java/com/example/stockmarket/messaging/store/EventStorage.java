package com.example.stockmarket.messaging.store;

import com.example.stockmarket.messaging.core.event.EventStorageManager;
import com.example.stockmarket.messaging.core.event.Event;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventStorage implements EventStorageManager {

    private final EventRepository repository;

    @Override
    public void persist(Event event) {
        repository.save(EventEntity.builder().aggregateId(event.getAggregateId()).content(event).build());
    }
}
