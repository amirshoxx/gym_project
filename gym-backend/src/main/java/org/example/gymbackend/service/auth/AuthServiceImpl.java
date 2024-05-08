package org.example.gymbackend.service.auth;

import org.example.gymbackend.dto.LoginDto;
import org.example.gymbackend.dto.RegisterDto;
import org.example.gymbackend.entity.Role;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.RoleRepo;
import org.example.gymbackend.repository.UserRepo;
import org.example.gymbackend.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    AuthenticationManager manager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> registerUser(RegisterDto registerDto) {
        List<Role> roleUser = roleRepo.findAllByName("ROLE_USER");
        User user = new User(registerDto.getFullName()
                , registerDto.getPhone(), passwordEncoder.encode(registerDto.getPassword()),"");
        userRepo.save(user);
        return ResponseEntity.ok("reg");
    }

    @Override
    public ResponseEntity<?> loginUser(LoginDto loginDto) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        User users = userRepo.findByFullName(loginDto.getUsername()).orElseThrow();
        UUID id = users.getId();
        Map<String, String> tokens = Map.of("token1", jwtService.getUserToken(users), "token2", jwtService.getUserRefreshToken(users),"id", String.valueOf(id));
        return ResponseEntity.ok(tokens);
    }

    @Override
    public ResponseEntity<?> refreshToken(String refreshToken) {
        String id = jwtService.parseToken(refreshToken);
        User users = userRepo.findById(UUID.fromString(id)).orElseThrow();
        return ResponseEntity.ok(jwtService.getUserToken(users));
    }
}
