package com.portfolioy0711.api.typings.response;

import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@ToString(of = { "reviewId", "placeId", "userId", "content", "rewarded", "photoIds" })
public class ReviewResponse {
    private String reviewId;
    private String placeId;
    private String userId;
    private String content;
    private int rewarded;
    private Set<String> photoIds = new HashSet<>();

    public ReviewResponse(String reviewId, String placeId, String userId, String content, int rewarded, Set<String> photoIds) {
        this.reviewId = reviewId;
        this.placeId = placeId;
        this.userId = userId;
        this.content = content;
        this.rewarded = rewarded;
        this.photoIds = photoIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewResponse response = (ReviewResponse) o;
        return rewarded == response.rewarded &&
                reviewId.equals(response.reviewId) &&
                placeId.equals(response.placeId) &&
                userId.equals(response.userId) &&
                content.equals(response.content) &&
                photoIds.equals(response.photoIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, placeId, userId, content, rewarded, photoIds);
    }
}
