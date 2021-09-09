package com.portfolioy0711.api.typings.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRewardReponse {
    @ApiModelProperty(value = "리워드 아이디", required = true)
    String rewardId;
    @ApiModelProperty(value = "리뷰글 아이디", required = true)
    String reviewId;
    @ApiModelProperty(value = "수행된 연산", required = true)
    String operation;
    @ApiModelProperty(value = "변경된 포인트", required = true)
    Integer pointDelta;
    @ApiModelProperty(value = "리워드 사유", required = true)
    String reason;

    @QueryProjection
    public UserRewardReponse(String rewardId, String reviewId, String operation, Integer pointDelta, String reason) {
        this.rewardId = rewardId;
        this.reviewId = reviewId;
        this.operation = operation;
        this.pointDelta = pointDelta;
        this.reason = reason;
    }
}
