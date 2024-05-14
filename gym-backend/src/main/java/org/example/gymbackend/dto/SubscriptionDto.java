package org.example.gymbackend.dto;

import java.util.UUID;

public record SubscriptionDto(String image, String fullName, String phoneNumber, UUID subscriptionId , UUID userId) {
}
