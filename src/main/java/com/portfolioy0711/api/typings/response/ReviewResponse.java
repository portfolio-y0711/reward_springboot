package com.portfolioy0711.api.typings.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
@ToString(of = { "reviewId", "placeId", "userId", "content", "rewarded", "photos" })
public class ReviewResponse {
    private String reviewId;
    private String placeId;
    private String userId;
    private String content;
    private int rewarded;
    private Set<String> photoIds;

    public ReviewResponse(String reviewId, String placeId, String userId, String content, int rewarded, Set<String> photos) {
        this.reviewId = reviewId;
        this.placeId = placeId;
        this.userId = userId;
        this.content = content;
        this.rewarded = rewarded;
        this.photoIds = photos;
    }

}
