package org.example.gymbackend.service;

import org.example.gymbackend.dto.SubscriptionTypeDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface SubscriptionTypeService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> save(SubscriptionTypeDto subscriptionType);

    ResponseEntity<?> update(UUID id, SubscriptionTypeDto dto);

    ResponseEntity<?> deleteItem(UUID id);
}
