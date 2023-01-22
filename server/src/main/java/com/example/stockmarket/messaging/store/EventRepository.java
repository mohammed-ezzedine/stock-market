package com.example.stockmarket.messaging.store;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends MongoRepository<EventEntity, Long> {
    List<EventEntity> getAllByAggregateId(UUID aggregateId);
}
