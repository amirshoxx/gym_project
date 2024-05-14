package org.example.gymbackend.repository;

import org.example.gymbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AdminRepo extends JpaRepository<User, UUID> {
    List<User> findByPhoneNumber(String phoneNumber);

}
