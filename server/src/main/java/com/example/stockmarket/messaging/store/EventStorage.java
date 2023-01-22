package com.example.stockmarket.messaging.store;

import com.example.stockmarket.messaging.core.event.Event;
import com.example.stockmarket.messaging.core.event.EventStorageManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventStorage implements EventStorageManager {

    private final EventRepository repository;

    @Override
    public void persist(Event event) {
        repository.save(EventEntity.builder().aggregateId(event.getAggregateId()).content(event).build());
    }

    @Override
    public List<Event> retrieveAggregateEvent(UUID aggregateId) {
        return repository.getAllByAggregateId(aggregateId).stream().map(EventEntity::getContent).collect(Collectors.toList());
    }
}
