package org.example.gymbackend.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record AdminDto(
        @NotBlank
        String fullName,
        @NotBlank
        String phoneNumber,
        @NotBlank
        String password,
        @NotBlank
        UUID gymId
) {
}
