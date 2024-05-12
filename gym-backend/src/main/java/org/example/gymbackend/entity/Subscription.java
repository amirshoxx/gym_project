package org.example.gymbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "subscription")
@Builder
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDate startTime;
    private LocalDate endTime;
    @ManyToOne
    private SubscriptionType subscriptionType;
    private Double price;
    private Boolean status;
    private Boolean limited;
    @ManyToOne
    private User user;
    private Integer dayCount;


    public Subscription(LocalDate startTime, LocalDate endTime, SubscriptionType subscriptionType, Double price, Boolean status, Boolean limited, User user, Integer dayCount) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.subscriptionType = subscriptionType;
        this.price = price;
        this.status = status;
        this.limited = limited;
        this.user = user;
        this.dayCount = dayCount;
    }

}
