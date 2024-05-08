package org.example.gymbackend.service.user;

import org.example.gymbackend.dto.RegisterDto;
import org.springframework.http.HttpEntity;

public interface UserService {
    HttpEntity<?> getAllUsers();

    HttpEntity<?> save(RegisterDto registerDto);
}
