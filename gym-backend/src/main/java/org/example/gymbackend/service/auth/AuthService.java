package org.example.gymbackend.service.auth;

import org.example.gymbackend.dto.LoginDto;
import org.example.gymbackend.dto.RegisterDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> registerUser(RegisterDto registerDto);

    ResponseEntity<?> loginUser(LoginDto loginDto);

    ResponseEntity<?> refreshToken(String refreshToken);
}
