package org.example.gymbackend.repository;

import org.example.gymbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByFullName(String fullName);

    Optional<User> findAllByChatId(Long chatId);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByPassword(String password);

    @Query(value = "select * from users u " +
            "inner join public.users_roles ur on u.id = ur.users_id " +
            "inner join public.roles r on r.id = ur.roles_id where r.name=:role"
            , nativeQuery = true)
    Optional<User> findByRole(String role);

}

