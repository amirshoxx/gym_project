package org.example.gymbackend.repository;

import org.example.gymbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByFullName(String fullName);

    Optional<User> findAllByChatId(Long chatId);
 Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findByGym_Id(UUID id);

    List<User> getAllByPhoneNumberContainingIgnoreCase(String phoneNumber);
}

