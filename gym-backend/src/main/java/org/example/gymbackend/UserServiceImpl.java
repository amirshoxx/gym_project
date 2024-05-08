package org.example.gymbackend;

import lombok.RequiredArgsConstructor;
import org.example.gymbackend.dto.AdminDto;
import org.example.gymbackend.entity.Gym;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.GymRepo;
import org.example.gymbackend.repository.UserRepo;
import org.example.gymbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final GymRepo gymRepo;
    private final UserRepo userRepo;


    @Override
    public ResponseEntity<?> saveAdmin(AdminDto dto) {
        User user = userRepo.findByPhoneNumber(dto.phoneNumber()).orElseThrow();

        Gym gym = gymRepo.findById(dto.gymId()).orElseThrow();
        return ResponseEntity.ok("User updated to Admin");
    }
}
