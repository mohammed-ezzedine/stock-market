package com.example.stockmarket.aggregate.core;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AggregateLinkWriteService {

    private final AggregateLinkStorageManager storageManager;

    public void linkAggregates(UUID aggregateId, UUID linkedAggregateId, String linkedAggregateName) {
        storageManager.saveAggregateLink(AggregateLink.builder().aggregateId(aggregateId).linkedAggregateId(linkedAggregateId).linkedAggregateName(linkedAggregateName).build());
    }
}
