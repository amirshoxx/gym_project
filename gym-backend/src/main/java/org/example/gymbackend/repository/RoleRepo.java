package org.example.gymbackend.repository;

import org.example.gymbackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    List<Role> findAllByName(String name);


}
