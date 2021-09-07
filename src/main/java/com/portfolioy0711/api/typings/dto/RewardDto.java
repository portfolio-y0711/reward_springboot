package com.portfolioy0711.api.typings.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class RewardDto {
    String rewardId;
    String userId;
    String operation;
    Integer pointDelta;
    String reason;

    @QueryProjection
    public RewardDto(String rewardId, String userId, String operation, Integer pointDelta, String reason) {
        this.rewardId = rewardId;
        this.userId = userId;
        this.operation = operation;
        this.pointDelta = pointDelta;
        this.reason = reason;
    }
}
