package org.example.gymbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "subscription_type")
@Builder
public class SubscriptionType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Double price;
    private String title;
    private Integer dayCount;

    public SubscriptionType(String name, Double price, String title, Integer dayCount) {
        this.name = name;
        this.price = price;
        this.title = title;
        this.dayCount = dayCount;
    }
}
