package com.portfolioy0711.api.typings.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReviewResponse {
    @ApiModelProperty(value = "리워드 포인트", required = true)
    String reviewId;
    @ApiModelProperty(value = "리워드 포인트", required = true)
    String placeId;
    @ApiModelProperty(value = "리워드 포인트", required = true)
    String userId;
    @ApiModelProperty(value = "리워드 포인트", required = true)
    String content;
    @ApiModelProperty(value = "리워드 포인트", required = true)
    Integer rewarded;
    @ApiModelProperty(value = "리워드 포인트", required = true)
    List<String> attachedPhotoIds;

    @QueryProjection
    public ReviewResponse(String reviewId, String placeId, String userId, String content, Integer rewarded, List<String> attachedPhotoIds) {
        this.reviewId = reviewId;
        this.placeId = placeId;
        this.userId = userId;
        this.content = content;
        this.rewarded = rewarded;
        this.attachedPhotoIds = attachedPhotoIds;
    }
}
