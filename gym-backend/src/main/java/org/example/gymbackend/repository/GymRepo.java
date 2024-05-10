package org.example.gymbackend.repository;

import org.example.gymbackend.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


public interface GymRepo extends JpaRepository<Gym, UUID> {
}
