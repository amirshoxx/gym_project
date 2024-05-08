package org.example.gymbackend.controller;

import jakarta.validation.Valid;
import org.example.gymbackend.dto.AdminDto;
import org.example.gymbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> saveAdmin(@RequestBody @Valid AdminDto dto) {
        return userService.saveAdmin(dto);
    }
}
