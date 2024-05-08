package org.example.gymbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymbackend.dto.RegisterDto;
import org.example.gymbackend.service.user.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpEntity<?> getAllUsers(){
        HttpEntity<?> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }


    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public HttpEntity<?> savePost(@RequestBody RegisterDto registerDto){
        HttpEntity<?> register = userService.save(registerDto);
        return ResponseEntity.ok(register);
    }



}
