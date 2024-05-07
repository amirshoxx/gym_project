package org.example.gymbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Date startTime;
    private Date endTime;
    @ManyToOne
    private SubscriptionType subscriptionType;
    private Double price;
    private Boolean status;
    private Boolean limited;
    @ManyToOne
    private User user;
    private Integer dayCount;
}
