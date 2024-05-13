package org.example.gymbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuperAdminDto {
    private String fullName;
    private String phoneNumber;
    private String password;
}
