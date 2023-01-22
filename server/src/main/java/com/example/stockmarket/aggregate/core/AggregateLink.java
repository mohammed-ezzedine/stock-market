package com.example.stockmarket.aggregate.core;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AggregateLink {
    private UUID aggregateId;
    private UUID linkedAggregateId;
    private String linkedAggregateName;
}
