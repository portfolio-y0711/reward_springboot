package com.portfolioy0711.api.data.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Reward {
    @Id
    String rewardId;
    //    String userId;
    String reviewId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    User user;
    String operation;
    Integer pointDelta;
    String reason;

    @Builder
    public Reward(String rewardId, String reviewId, User user, String operation, Integer pointDelta, String reason) {
        this.rewardId = rewardId;
        this.reviewId = reviewId;
        this.user = user;
        this.operation = operation;
        this.pointDelta = pointDelta;
        this.reason = reason;
    }
}
