package org.example.gymbackend.service.subscriptions;

import lombok.RequiredArgsConstructor;
import org.example.gymbackend.dto.SubscriptionDto;
import org.example.gymbackend.entity.Subscription;
import org.example.gymbackend.entity.SubscriptionType;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.SubscriptionRepo;
import org.example.gymbackend.repository.SubscriptionTypeRepo;
import org.jvnet.hk2.annotations.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@Component
public class SubscriptionImpl implements SubscriptionsService {
  private final SubscriptionTypeRepo subscriptionRepo;
  private final SubscriptionRepo repo;

    @Override
    public HttpEntity<?> save(SubscriptionDto dto) {
        User user = new User(dto.fullName(), dto.phoneNumber(), null, dto.image());
        SubscriptionType subscriptionType = subscriptionRepo.findById(dto.subscriptionId()).get();
        LocalDate localDate = LocalDate.now().plusDays(subscriptionType.getDayCount());
        Subscription subscription = new Subscription();
        subscription.setId(UUID.randomUUID());
        subscription.setStartTime(LocalDate.now());
        subscription.setEndTime(localDate);
        subscription.setSubscriptionType(subscriptionType);
        subscription.setUser(user);
        subscription.setPrice(subscriptionType.getPrice());
        Subscription save = repo.save(subscription);
        return ResponseEntity.ok(save);
    }
}
