package com.portfolioy0711.api.data.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    String userId;
    String name;
    Integer rewardPoint;

    @Builder
    public User(String userId, String name, Integer rewardPoint) {
        this.userId = userId;
        this.name = name;
        this.rewardPoint = rewardPoint;
    }
}
