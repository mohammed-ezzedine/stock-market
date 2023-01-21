package com.example.stockmarket.messaging.core.event;

import java.io.Serializable;
import java.util.UUID;

public interface Event extends Serializable {

    UUID getAggregateId();
}
