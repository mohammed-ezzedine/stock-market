package com.example.stockmarket.messaging.store;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@Data
@Builder
public class EventEntity {

    @Transient
    public static final String SEQUENCE_NAME = "event_sequence";

    @Id
    private long id;
    @Indexed
    private UUID aggregateId;
    private Object content;
}
