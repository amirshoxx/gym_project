package org.example.gymbackend.service.subscriptions;

import org.example.gymbackend.dto.SubscriptionDto;
import org.springframework.http.HttpEntity;

import java.util.UUID;

public interface SubscriptionsService {
    HttpEntity<?>getAllSubscriptions();
    HttpEntity<?> save(SubscriptionDto dto);
    HttpEntity<?> edite(UUID id);
    HttpEntity<?> patch(UUID id);
    HttpEntity<?> search(String phoneNumber);
    HttpEntity<?> selectSubject(SubscriptionDto dto,UUID subscId);
}
