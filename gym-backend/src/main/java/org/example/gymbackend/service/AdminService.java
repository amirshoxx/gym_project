package org.example.gymbackend.service;

import org.example.gymbackend.dto.AdminDto;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<?> saveAdmin(AdminDto dto);
}
