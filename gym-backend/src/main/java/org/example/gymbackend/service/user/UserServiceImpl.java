package org.example.gymbackend.service.user;


import lombok.RequiredArgsConstructor;
import org.example.gymbackend.dto.LoginDto;
import org.example.gymbackend.dto.RegisterDto;
import org.example.gymbackend.entity.Role;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.RoleRepo;
import org.example.gymbackend.repository.UserRepo;
import org.example.gymbackend.service.jwt.JwtService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    private final
    PasswordEncoder passwordEncoder;
    private final
    AuthenticationManager authenticationManager;
    private final
    JwtService jwtService;



    @Override
    public HttpEntity<?> getAllUsers() {
        List<User> allUsers = userRepo.findAll();
        return ResponseEntity.ok(allUsers);
    }

    @Override
    public HttpEntity<?> save(RegisterDto dto) {
        Optional<Role> roleUser = roleRepo.findByName("ROLE_USER");
        User user  = new User();
        user.setId(UUID.randomUUID());
        user.setFullName(dto.getFullName());
        user.setPhoneNumber(dto.getPhone());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(List.of(roleUser.get()));
        User saved = userRepo.save(user);
        return ResponseEntity.ok(saved);
    }




}
