package org.example.gymbackend.controller;
import lombok.RequiredArgsConstructor;
import org.example.gymbackend.dto.LoginDto;
import org.example.gymbackend.dto.RegisterDto;
import org.example.gymbackend.service.auth.AuthService;
import org.example.gymbackend.service.jwt.JwtService;
import org.example.gymbackend.service.user.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final JwtService jwtService;
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpEntity<?> getAllUsers(){
        HttpEntity<?> allUsers = userService.getAllUsers();
        return allUsers;
    }
    @GetMapping("/getId")
    public HttpEntity<?> getUserId(@RequestHeader String refreshToken){
        UUID userId = UUID.fromString(jwtService.extractJwt(refreshToken).getPayload().getSubject());
        return ResponseEntity.ok(userId);
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> savePost(@RequestBody RegisterDto registerDto){
        HttpEntity<?> register = userService.save(registerDto);
        return ResponseEntity.ok(register);
    }

    @PostMapping("/login")
    public HttpEntity<?> generate(@RequestBody LoginDto dto) {
        Map<String, String> stringStringMap = authService.loginUser(dto);
        return ResponseEntity.ok(stringStringMap);
    }

    //bundan maqsad admin va super_adminni ajratish
    @GetMapping("/admins")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public HttpEntity<?> admins() {
        return ResponseEntity.status(200).body("super_admin");
    }

    @GetMapping("/super_admins")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public HttpEntity<?> super_admins() {
        return ResponseEntity.status(200).body("super_admin");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpEntity<?> admin() {
        return ResponseEntity.status(200).body("admin");
    }

    @PostMapping("/refresh")
    public HttpEntity<?> generateRefresh(@RequestHeader String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
}
