package com.portfolioy0711.api.typings.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
public class UserRewardReponse {
    String rewardId;
    String userId;
    String reviewId;
    String operation;
    Integer pointDelta;
    String reason;
    LocalDateTime created_at;

    @QueryProjection
    public UserRewardReponse(String rewardId, String userId, String reviewId, String operation, Integer pointDelta, String reason , LocalDateTime created_at) {
        this.rewardId = rewardId;
        this.userId = userId;
        this.reviewId = reviewId;
        this.operation = operation;
        this.pointDelta = pointDelta;
        this.reason = reason;
        this.created_at = created_at;
    }
}
