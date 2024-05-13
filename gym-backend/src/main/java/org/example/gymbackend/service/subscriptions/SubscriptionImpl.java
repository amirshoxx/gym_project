package org.example.gymbackend.service.subscriptions;

import lombok.RequiredArgsConstructor;
import org.example.gymbackend.dto.SubscriptionDto;
import org.example.gymbackend.entity.Role;
import org.example.gymbackend.entity.Subscription;
import org.example.gymbackend.entity.SubscriptionType;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.RoleRepo;
import org.example.gymbackend.repository.SubscriptionRepo;
import org.example.gymbackend.repository.SubscriptionTypeRepo;
import org.example.gymbackend.repository.UserRepo;
import org.jvnet.hk2.annotations.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@Component
public class SubscriptionImpl implements SubscriptionsService {
  private final SubscriptionTypeRepo subscriptionRepo;
  private final SubscriptionRepo repo;
  private final RoleRepo roleRepo;
private final UserRepo userRepo;
    @Override
    public HttpEntity<?> save(SubscriptionDto dto) {
        List<Role> roleUser = roleRepo.findAllByName("ROLE_USER");
        Optional<User> existingUserOptional = userRepo.findByPhoneNumber(dto.phoneNumber());
        User user;
        if (existingUserOptional.isPresent()) {
            user = existingUserOptional.get();
        } else {
            user = new User(dto.fullName(), dto.phoneNumber(), null, "df77645f-a305-404a-b07b-1670d909f7c2user.png",roleUser);
            user = userRepo.save(user);
        }
        SubscriptionType subscriptionType = subscriptionRepo.findById(dto.subscriptionId()).orElseThrow(() -> new RuntimeException("Subscription type not found"));
        LocalDate localDate = LocalDate.now().plusDays(subscriptionType.getDayCount());
        Subscription subscription = new Subscription();
        subscription.setId(UUID.randomUUID());
        subscription.setStartTime(LocalDate.now());
        subscription.setEndTime(localDate);
        subscription.setStatus(true);
        subscription.setLimited(false);
        subscription.setSubscriptionType(subscriptionType);
        subscription.setUser(user);
        subscription.setDayCount(subscriptionType.getDayCount());
        subscription.setPrice(subscriptionType.getPrice());
        Subscription save = repo.save(subscription);
        return ResponseEntity.ok(save);
    }

}
