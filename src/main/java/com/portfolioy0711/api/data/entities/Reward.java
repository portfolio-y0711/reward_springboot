package com.portfolioy0711.api.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Reward {
    @Id
    String rewardId;
    String userId;
    String operation;
    Integer pointDelta;
    String reason;

    @Builder
    public Reward(String rewardId, String userId, String operation, Integer pointDelta, String reason) {
        this.rewardId = rewardId;
        this.userId = userId;
        this.operation = operation;
        this.pointDelta = pointDelta;
        this.reason = reason;
    }
}
