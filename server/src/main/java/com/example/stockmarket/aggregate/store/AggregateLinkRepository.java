package com.example.stockmarket.aggregate.store;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface AggregateLinkRepository extends MongoRepository<AggregateLinkEntity, UUID> {
    AggregateLinkEntity getByAggregateIdAndLinkedAggregateName(UUID id, String linkedAggregateName);
}
