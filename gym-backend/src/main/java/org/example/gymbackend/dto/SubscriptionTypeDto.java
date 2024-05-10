package org.example.gymbackend.dto;

import lombok.Getter;

@Getter
public class SubscriptionTypeDto {
    private String name;
    private Double price;
    private String title;
    private Integer dayCount;
}
