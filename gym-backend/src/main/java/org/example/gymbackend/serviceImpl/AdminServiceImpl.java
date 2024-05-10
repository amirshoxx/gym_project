package org.example.gymbackend.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.gymbackend.dto.AdminDto;
import org.example.gymbackend.entity.Gym;
import org.example.gymbackend.entity.Role;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.GymRepo;
import org.example.gymbackend.repository.RoleRepo;
import org.example.gymbackend.repository.UserRepo;
import org.example.gymbackend.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final GymRepo gymRepo;
    private final UserRepo userRepo;
    private final RoleRepo  roleRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity<?> saveAdmin(AdminDto dto) {
        User user = userRepo.findByPhoneNumber(dto.phoneNumber()).orElseThrow();
        user.setFullName(dto.fullName());
        user.setPassword(passwordEncoder.encode(dto.password()));
        List<Role> roleAdmin = roleRepo.findAllByName("ROLE_ADMIN");
        user.setRoles(roleAdmin);
        User userUpdatedToAdmin = userRepo.save(user);
        Gym gym = gymRepo.findById(dto.gymId()).orElseThrow();
        gym.getAdmins().add(userUpdatedToAdmin);
        gymRepo.save(gym);
        return ResponseEntity.ok("User updated to Admin");
    }
}
