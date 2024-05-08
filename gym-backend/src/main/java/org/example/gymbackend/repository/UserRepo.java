package org.example.gymbackend.repository;

import org.example.gymbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByFullName(String fullName);
    Optional<User> findAllByChatId(Long chatId);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByPassword(String password);

}
