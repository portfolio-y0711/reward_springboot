package com.portfolioy0711.api.typings.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRewardPointDto {
    @ApiModelProperty(value = "리워드 포인트", required = true)
    private Integer rewardPoint;
}
