package com.example.stockmarket.messaging.store;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<EventEntity, Long> {
}
