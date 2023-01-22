package com.example.stockmarket.aggregate.core;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AggregateLinkReadService {

    private final AggregateLinkStorageManager storageManager;

    public UUID getLinkedAggregate(UUID aggregateId, String linkedAggregateName) {
        return storageManager.getAggregateLink(aggregateId, linkedAggregateName).getLinkedAggregateId();
    }
}
