package org.example.gymbackend.service.user;// UserServiceImplTest.java

import org.example.gymbackend.dto.RegisterDto;
import org.example.gymbackend.entity.Role;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.RoleRepo;
import org.example.gymbackend.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;


    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    private UserRepo userRepo;

    @Mock
    private RoleRepo roleRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers() {
        List<Role> roleUser = roleRepo.findAllByName("ROLE_USER");
        List<User> mockUsers = new ArrayList<>();
        User user = new User("Amir", "+998914174470", "123", "balu", roleUser);
        mockUsers.add(user);
        when(userRepo.findAll()).thenReturn(mockUsers);
        HttpEntity<?> result = userServiceImpl.getAllUsers();
        assertEquals(ResponseEntity.ok(mockUsers), result);
    }


    @Test
    void save() {
        RegisterDto registerDto = new RegisterDto("Amir", "balu", passwordEncoder.encode("123"), "+998914174470");
        Role role = new Role(1L, "ROLE_USER");
        when(roleRepo.findByName("ROLE_USER")).thenReturn(Optional.of(role));
        userServiceImpl.save(registerDto);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepo).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals(savedUser.getFullName(), registerDto.getFullName());
        assertEquals(savedUser.getImage(), registerDto.getImage());
        assertEquals(savedUser.getPassword(), registerDto.getPassword());
        assertEquals(savedUser.getPhoneNumber(), registerDto.getPhone());
    }

}
