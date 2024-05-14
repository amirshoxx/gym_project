package org.example.gymbackend.service.subscriptions;

import lombok.RequiredArgsConstructor;
import org.example.gymbackend.dto.SubscriptionDto;
import org.example.gymbackend.entity.Subscription;
import org.example.gymbackend.entity.SubscriptionType;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.projection.SubscriptionProjection;
import org.example.gymbackend.repository.RoleRepo;
import org.example.gymbackend.repository.SubscriptionRepo;
import org.example.gymbackend.repository.SubscriptionTypeRepo;
import org.example.gymbackend.repository.UserRepo;
import org.jvnet.hk2.annotations.Service;
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
    private final UserRepo userRepo;

    @Override
    public HttpEntity<?> getAllSubscriptions() {
        List<SubscriptionProjection> subsc = repo.getSubsc();
        return ResponseEntity.ok(subsc);
    }

    @Override
    public HttpEntity<?> save(SubscriptionDto dto) {
        Optional<Subscription> byUserId = repo.findByUserId(dto.userId());
        Optional<User> existingUser = userRepo.findByPhoneNumber(dto.phoneNumber());
        SubscriptionType subscriptionType = subscriptionRepo.findById(dto.subscriptionId()).orElse(null);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (byUserId.isPresent()) {
                Subscription subscription = byUserId.get();
                subscription.setPrice(subscriptionType.getPrice());
                subscription.setName(subscriptionType.getName());
                subscription.setUser(user);
                subscription = repo.save(subscription);
                return ResponseEntity.ok(subscription);
            } else {
                Subscription subscription = new Subscription();
                subscription.setId(UUID.randomUUID());
                subscription.setStatus(true);
                subscription.setLimited(false);
                subscription.setUser(user);
                subscription.setName(subscriptionType.getName());
                subscription.setSubscriptionType(subscriptionType);
                subscription.setDayCount(subscriptionType.getDayCount());
                subscription.setPrice(subscriptionType.getPrice());
                subscription = repo.save(subscription);
                return ResponseEntity.ok(subscription);
            }
        } else {
            User newUser = new User();
            newUser.setFullName(dto.fullName());
            newUser.setPhoneNumber(dto.phoneNumber());
            newUser.setImage("df77645f-a305-404a-b07b-1670d909f7c2user.png");
            newUser = userRepo.save(newUser);
            Subscription subscription = new Subscription();
            subscription.setId(UUID.randomUUID());
            subscription.setStatus(true);
            subscription.setLimited(false);
            subscription.setUser(newUser);
            subscription.setName(subscriptionType.getName());
            subscription.setSubscriptionType(subscriptionType);
            subscription.setDayCount(subscriptionType.getDayCount());
            subscription.setPrice(subscriptionType.getPrice());
            // Set other subscription properties if needed
            subscription = repo.save(subscription);
            return ResponseEntity.ok(subscription);
        }
    }


    @Override
    public HttpEntity<?> edite(UUID id) {
        Subscription subscription = repo.findById(id).get();
        subscription.setStartTime(LocalDate.now());
        subscription.setDayCount(subscription.getDayCount()-1);
        subscription.setEndTime(LocalDate.now().plusDays(subscription.getDayCount()));
        Subscription save = repo.save(subscription);
        return ResponseEntity.ok(save);
    }


    @Override
    public HttpEntity<?> search(String phoneNumber) {
        List<SubscriptionProjection> search = repo.search(phoneNumber);
        return ResponseEntity.ok(search);
    }

    @Override
    public HttpEntity<?> selectSubject(SubscriptionDto dto , UUID subscId) {
        SubscriptionType subscriptionType = subscriptionRepo.findById(dto.subscriptionId()).get();
        Subscription subscription = repo.findById(subscId).get();
        if(subscription.getDayCount()<=0){
            subscription.setName(subscriptionType.getName());
            subscription.setStatus(true);
            subscription.setPrice(subscriptionType.getPrice());
            subscription.setStartTime(LocalDate.now());
            subscription.setDayCount(subscriptionType.getDayCount());
            subscription.setEndTime(LocalDate.now().plusDays(subscriptionType.getDayCount()));
          repo.save(subscription);
            return ResponseEntity.ok("Tarifingiz muvafaqiyati qo'shildi");
        }   else {
            return ResponseEntity.ok("Tarifingiz mavjud");
        }
    }

    @Override
    public HttpEntity<?> patch(UUID id) {
        Subscription subscription = repo.findById(id).get();
        if(subscription.getDayCount()<=1){
         subscription.setStatus(false);
         subscription.setDayCount(0);
         repo.save(subscription);
        }else {
            subscription.setDayCount(subscription.getDayCount()-1);
          repo.save(subscription);
        }
        return ResponseEntity.ok(subscription);
    }

}
