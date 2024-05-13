package org.example.gymbackend.service.subscriptions;

import org.example.gymbackend.dto.SubscriptionDto;
import org.springframework.http.HttpEntity;

public interface SubscriptionsService {
    HttpEntity<?> save(SubscriptionDto dto);
}
