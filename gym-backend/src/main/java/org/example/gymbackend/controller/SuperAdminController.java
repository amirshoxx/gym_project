package org.example.gymbackend.controller;

import org.example.gymbackend.dto.SuperAdminDto;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/super_admin")
public class SuperAdminController {

    @Autowired
    UserRepo userRepo;

    @PatchMapping
    public ResponseEntity<?> editSuperAdmin(@RequestBody SuperAdminDto superAdminDto, @RequestParam UUID id) {


        User user = userRepo.findById(id).orElseThrow();
        user.setPassword(superAdminDto.getPassword());
        user.setPhoneNumber(superAdminDto.getPhoneNumber());
        user.setFullName(superAdminDto.getFullName());
        userRepo.save(user);
        return ResponseEntity.ok(user);
    }
}
