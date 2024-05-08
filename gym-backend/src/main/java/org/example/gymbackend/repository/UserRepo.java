package org.example.gymbackend.repository;

import org.example.gymbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByFullName(String fullName);
    Optional<User> findAllByChatId(Long chatId);

}
