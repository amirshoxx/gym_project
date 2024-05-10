package org.example.gymbackend.repository;

import org.example.gymbackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, Long> {
    List<Role> findAllByName(String name);
    Optional<Role> findByName(String name);
}
