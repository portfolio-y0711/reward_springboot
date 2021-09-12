package com.portfolioy0711.api.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString(of = { "rewardId", "reviewId", "operation", "pointDelta", "reason" })
@NoArgsConstructor
public class Reward extends Base {
    @Id
    private String rewardId;
    private String reviewId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String operation;

    @Column(nullable = false)
    private Integer pointDelta;

    @Column(nullable = false)
    private String reason;

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
