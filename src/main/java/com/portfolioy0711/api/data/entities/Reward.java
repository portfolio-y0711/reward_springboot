package com.portfolioy0711.api.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(of = { "rewardId", "reviewId", "operation", "pointDelta", "reason" })
@NoArgsConstructor
public class Reward extends Base {
    @Id
    String rewardId;
    String reviewId;

    @JsonIgnore
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
