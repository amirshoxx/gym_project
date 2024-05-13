package org.example.gymbackend.controller;

import jakarta.validation.Valid;
import org.example.gymbackend.dto.AdminDto;
import org.example.gymbackend.entity.Gym;
import org.example.gymbackend.entity.Role;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.AdminRepo;
import org.example.gymbackend.repository.GymRepo;
import org.example.gymbackend.repository.RoleRepo;
import org.example.gymbackend.repository.UserRepo;
import org.example.gymbackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    GymRepo gymRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    AdminRepo adminRepo;


    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    @PutMapping
    public ResponseEntity<?> saveAdmin(@RequestBody @Valid AdminDto adminDto) {


        Gym gym = gymRepo.findById(UUID.fromString(adminDto.getGymId())).orElseThrow();
        List<Role> roleAdmin = roleRepo.findAllByName("ROLE_ADMIN");
        List<User> byPhoneNumber = adminRepo.findByPhoneNumber(adminDto.getPhoneNumber());

        for (User user1 : byPhoneNumber) {
            if (user1.getPhoneNumber().equals(adminDto.getPhoneNumber())) {
                User user = userRepo.findById(user1.getId()).orElseThrow();
                user.setFullName(adminDto.getFullName());
                user.setPassword(adminDto.getPassword());
                user.setGym(gym);
                user.setRoles(roleAdmin);
                List<User> save = Collections.singletonList(userRepo.save(user));
                return ResponseEntity.ok(save);
            }
        }

        return ResponseEntity.ok("Oldin kontaktni kiriting!!!");

    }
}
