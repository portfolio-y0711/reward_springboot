package com.portfolioy0711.api.data.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Review {
    @Id
    String reviewId;
    String placeId;
    String content;
    String userId;
    Integer rewarded;

    @Builder
    public Review(String reviewId, String placeId, String content, String userId, Integer rewarded) {
        this.reviewId = reviewId;
        this.placeId = placeId;
        this.content = content;
        this.userId = userId;
        this.rewarded = rewarded;
    }
}
