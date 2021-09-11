package com.portfolioy0711.api.typings.response;

import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
@ToString(of = { "reviewId", "placeId", "userId", "content", "rewarded", "photos" })
public class ReviewResponse {
    @ApiModelProperty(value = "리뷰 아이디", required = true)
    private String reviewId;
    @ApiModelProperty(value = "여행장소 아이디", required = true)
    private String placeId;
    @ApiModelProperty(value = "유저 아이디", required = true)
    private String userId;
    @ApiModelProperty(value = "글내용 아이디", required = true)
    private String content;
    @ApiModelProperty(value = "리워드 여부", required = true)
    private int rewarded;
    @ApiModelProperty(value = "첨부 사진 아이디 세트", required = true)
    private Set<String> photos;

    public ReviewResponse(String reviewId, String placeId, String userId, String content, int rewarded, Set<String> photos) {
        this.reviewId = reviewId;
        this.placeId = placeId;
        this.userId = userId;
        this.content = content;
        this.rewarded = rewarded;
        this.photos = photos;
    }

}
