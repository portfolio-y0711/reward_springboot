package com.portfolioy0711.api.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Reward {
    @Id
    String rewardId;
//    String userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    User user;
    String operation;
    Integer pointDelta;
    String reason;

    @Builder
    public Reward(String rewardId, User user, String operation, Integer pointDelta, String reason) {
        this.rewardId = rewardId;
        this.user = user;
        this.operation = operation;
        this.pointDelta = pointDelta;
        this.reason = reason;
    }
}
