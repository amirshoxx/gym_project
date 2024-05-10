package org.example.gymbackend.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.example.gymbackend.dto.LoginDto;
import org.example.gymbackend.dto.RegisterDto;
import org.example.gymbackend.entity.Role;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.RoleRepo;
import org.example.gymbackend.repository.UserRepo;
import org.example.gymbackend.service.jwt.JwtService;
import org.example.gymbackend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> registerUser(RegisterDto registerDto) {
        List<Role> roleUser = roleRepo.findAllByName("ROLE_USER");
        User user = new User(registerDto.getFullName()
                , registerDto.getPhone(), passwordEncoder.encode(registerDto.getPassword()), "");
        userRepo.save(user);
        return ResponseEntity.ok("reg");
    }

    @Override
    public Map<String, String> loginUser(LoginDto dto) {
        Optional<User> user = userRepo.findByPhoneNumber(dto.getPhoneNumber());
        if(user.isPresent()){
            UUID id=user.get().getId();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getPhoneNumber(),
                            dto.getPassword()
                    )
            );
            String jwt = jwtService.generateJwt(id.toString());
            String refreshJwt = jwtService.generateJwtRefresh(id.toString());

            // Fetching the user's roles
            Set<String> roles = user.get().getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());

            // Including roles in the response map
            return Map.of("access_token", jwt, "refresh_token", refreshJwt, "roles", String.join(",", roles));
        } else {
            return null;
        }
    }

    @Override
    public String refreshToken(String refreshToken) {
        Jws<Claims> claimsJws = jwtService.extractJwt(refreshToken);
        Claims payload = claimsJws.getPayload();
        String id = payload.getSubject();
        return jwtService.generateJwt(id);
    }
}
