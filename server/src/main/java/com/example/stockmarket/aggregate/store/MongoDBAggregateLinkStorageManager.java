package com.example.stockmarket.aggregate.store;

import com.example.stockmarket.aggregate.core.AggregateLink;
import com.example.stockmarket.aggregate.core.AggregateLinkStorageManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MongoDBAggregateLinkStorageManager implements AggregateLinkStorageManager {

    private final AggregateLinkRepository repository;

    @Override
    public AggregateLink getAggregateLink(UUID aggregateId, String linkedAggregateName) {
        AggregateLinkEntity entity = repository.getByAggregateIdAndLinkedAggregateName(aggregateId, linkedAggregateName);
        return AggregateLink.builder().aggregateId(entity.getAggregateId()).linkedAggregateId(entity.getLinkedAggregateId())
                .linkedAggregateName(entity.getLinkedAggregateName()).build();
    }

    @Override
    public void saveAggregateLink(AggregateLink aggregateLink) {
        repository.save(AggregateLinkEntity.builder().id(UUID.randomUUID()).linkedAggregateId(aggregateLink.getLinkedAggregateId())
                .aggregateId(aggregateLink.getAggregateId()).linkedAggregateName(aggregateLink.getLinkedAggregateName()).build());
    }
}
