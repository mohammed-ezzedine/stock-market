package com.example.stockmarket.messaging.store;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventEntityListener extends AbstractMongoEventListener<EventEntity> {

    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<EventEntity> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGeneratorService.generateSequence(EventEntity.SEQUENCE_NAME));
        }
    }
}
