package org.example.gymbackend.service.auth;

import org.example.gymbackend.dto.LoginDto;
import org.example.gymbackend.dto.RegisterDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {
    ResponseEntity<?> registerUser(RegisterDto registerDto);

    Map<String, String> loginUser(LoginDto loginDto);

    String refreshToken(String refreshToken);
}
