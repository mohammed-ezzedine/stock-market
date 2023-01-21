package com.example.stockmarket.aggregate.core;

import java.util.UUID;

public interface AggregateLinkStorageManager {
    AggregateLink getAggregateLink(UUID aggregateId, String linkedAggregateName);

    void saveAggregateLink(AggregateLink aggregateLink);
}
