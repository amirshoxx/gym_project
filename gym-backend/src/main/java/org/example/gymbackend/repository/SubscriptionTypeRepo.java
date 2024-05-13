package org.example.gymbackend.repository;

import org.example.gymbackend.entity.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SubscriptionTypeRepo extends JpaRepository<SubscriptionType, UUID> {
}
