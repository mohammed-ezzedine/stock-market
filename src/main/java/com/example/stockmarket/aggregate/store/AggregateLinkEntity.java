package com.example.stockmarket.aggregate.store;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Builder
@Document
public class AggregateLinkEntity {
    @Id
    private UUID id;

    private UUID aggregateId;

    private UUID linkedAggregateId;

    private String linkedAggregateName;
}
