package org.example.gymbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "subscription_type")
public class SubscriptionType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid default gen_random_uuid()")
    private UUID id;
    private String name;
    private Double price;
    private Integer dayCount;
     private boolean datType;
    public SubscriptionType(String name, Double price, Integer dayCount) {
        this.name = name;
        this.price = price;
        this.dayCount = dayCount;
    }
}
