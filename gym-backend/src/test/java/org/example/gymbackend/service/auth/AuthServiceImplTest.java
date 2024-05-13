package org.example.gymbackend.service.auth;

import org.example.gymbackend.dto.LoginDto;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.UserRepo;
import org.example.gymbackend.service.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {
    @Mock
    private UserRepo userRepo;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginUser() {
        LoginDto loginDto = new LoginDto("+998936249882","123");
        String userId = "d740c6ea-8239-4186-b32f-6ad8397492e5";
        String accessToken = "access_token";
        String refreshToken = "refresh_token";
        User user = new User();
        user.setId(UUID.fromString(userId));
        when(userRepo.findByPhoneNumber(loginDto.getPhoneNumber())).thenReturn(Optional.of(user));
        when(jwtService.generateJwt(userId)).thenReturn(accessToken);
        when(jwtService.generateJwtRefresh(userId)).thenReturn(refreshToken);

        Map<String, String> tokens = authService.loginUser(loginDto);

        assertEquals(2, tokens.size());
        assertEquals(accessToken, tokens.get("access_token"));
        assertEquals(refreshToken, tokens.get("refresh_token"));
        verify(authenticationManager).authenticate(any());
    }
    @Test
    void loginUser_WithInvalidCredentials_ShouldReturnNull() {
        LoginDto loginDto = new LoginDto("+998936249882", "123");
        when(userRepo.findByPhoneNumber(loginDto.getPhoneNumber())).thenReturn(Optional.empty());
        Map<String, String> tokens = authService.loginUser(loginDto);
        assertEquals(null, tokens);
        verify(authenticationManager, never()).authenticate(any());
    }

}
