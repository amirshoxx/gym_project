package org.example.gymbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginDto {
    private String phoneNumber;
    private String password;
}
