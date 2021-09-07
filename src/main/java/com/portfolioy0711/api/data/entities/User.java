package com.portfolioy0711.api.data.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "userId")
    String userId;
    String name;
    Integer rewardPoint;

    @OneToMany(mappedBy = "user")
    List<Review> reviewList = new ArrayList<Review>();

    @Builder
    public User(String userId, String name, Integer rewardPoint) {
        this.userId = userId;
        this.name = name;
        this.rewardPoint = rewardPoint;
    }
}
