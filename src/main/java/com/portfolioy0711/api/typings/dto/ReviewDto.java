package com.portfolioy0711.api.typings.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.List;

@Data
public class ReviewDto extends EventDto {
    private String reviewId;
    private String placeId;
    private String userId;
    private String content;
    private Integer rewarded;
//    private List<String> photoIds;

    @QueryProjection
    public ReviewDto(String reviewId, String placeId, String userId, String content, Integer rewarded, List<String> photoIds) {
        this.reviewId = reviewId;
        this.placeId = placeId;
        this.userId = userId;
        this.content = content;
        this.rewarded = rewarded;
//        this.photoIds = photoIds;
    }
}
