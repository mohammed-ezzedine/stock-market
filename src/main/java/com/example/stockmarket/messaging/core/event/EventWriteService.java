package com.example.stockmarket.messaging.core.event;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventWriteService {

    private final EventStorageManager storageManager;

    public void persist(Event event) {
        storageManager.persist(event);
    }
}
