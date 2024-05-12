package org.example.gymbackend.serviceImpl;
import lombok.RequiredArgsConstructor;
import org.example.gymbackend.dto.SubscriptionTypeDto;
import org.example.gymbackend.entity.SubscriptionType;
import org.example.gymbackend.repository.SubscriptionTypeRepo;
import org.example.gymbackend.service.SubscriptionTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SubscriptionTypeImpl implements SubscriptionTypeService {
    private final SubscriptionTypeRepo subscriptionTypeRepo;
    @Override
    public ResponseEntity<?> getAll() {
        List<SubscriptionType> all = subscriptionTypeRepo.findAll();
        return ResponseEntity.ok(all);
    }

    @Override
    public ResponseEntity<?> save(SubscriptionTypeDto dto) {
        subscriptionTypeRepo.save(new SubscriptionType(dto.getName(),dto.getPrice(),  dto.getDayCount()));
        return ResponseEntity.ok("success");
    }

    @Override
    public ResponseEntity<?> update(UUID id, SubscriptionTypeDto dto) {
        SubscriptionType subscriptionType = subscriptionTypeRepo.findById(id).orElseThrow();
        if (dto.getName() != null && dto.getPrice() != null && dto.getTitle() != null && dto.getDayCount() != null){
            subscriptionType.setName(dto.getName());
            subscriptionType.setPrice(dto.getPrice());
            subscriptionType.setDayCount(dto.getDayCount());
        }
        return ResponseEntity.ok("update success");
    }

    @Override
    public ResponseEntity<?> deleteItem(UUID id) {
        subscriptionTypeRepo.deleteById(id);
        return ResponseEntity.ok("deleted");
    }
}
