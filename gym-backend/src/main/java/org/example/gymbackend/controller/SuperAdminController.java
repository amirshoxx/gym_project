package org.example.gymbackend.controller;

import org.example.gymbackend.dto.SuperAdminDto;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/super_admin")
public class SuperAdminController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PutMapping
    public ResponseEntity<?> editSuperAdmin(@RequestBody SuperAdminDto superAdminDto, @RequestParam String id) {

        User user = userRepo.findById(UUID.fromString(id)).orElseThrow();
        user.setPassword(passwordEncoder.encode(superAdminDto.getPassword()));
        user.setPhoneNumber(superAdminDto.getPhoneNumber());
        user.setFullName(superAdminDto.getFullName());
        userRepo.save(user);
        return ResponseEntity.ok(user);
    }
}
