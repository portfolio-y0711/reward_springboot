package com.portfolioy0711.api.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
public class Reward {
    @Id
    String rewardId;
//    String userId;
    @ManyToOne
    @JoinColumn(name = "userId")
    User user;
    String operation;
    Integer pointDelta;
    String reason;

    @Builder
//    public Reward(String rewardId, String userId, String operation, Integer pointDelta, String reason) {
    public Reward(String rewardId, User user, String operation, Integer pointDelta, String reason) {
        this.rewardId = rewardId;
//        this.userId = userId;
        this.user = user;
        this.operation = operation;
        this.pointDelta = pointDelta;
        this.reason = reason;
    }
}
