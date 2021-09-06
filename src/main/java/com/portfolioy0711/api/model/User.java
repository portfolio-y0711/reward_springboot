package com.portfolioy0711.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class User {
    @Id
    String userId;
    String name;
    Integer rewardPoint;
}
