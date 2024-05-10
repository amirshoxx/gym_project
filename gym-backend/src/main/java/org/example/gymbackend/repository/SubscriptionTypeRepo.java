package org.example.gymbackend.repository;

import org.example.gymbackend.entity.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubscriptionTypeRepo extends JpaRepository<SubscriptionType, UUID> {
}
