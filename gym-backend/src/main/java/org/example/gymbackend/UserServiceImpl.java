package org.example.gymbackend;

import org.example.gymbackend.dto.AdminDto;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.UserRepo;
import org.example.gymbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Override
    public ResponseEntity<?> saveAdmin(AdminDto dto) {
        User user = userRepo.findByPhoneNumber(dto.phoneNumber()).orElseThrow();

        return null;
    }
}
