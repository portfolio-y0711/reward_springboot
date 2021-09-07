package com.portfolioy0711.api.data.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Review {
    @Id
    String reviewId;
//    String placeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placeId")
    Place place;

    String content;
//    String userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    User user;
    Integer rewarded;

    @Builder
    public Review(String reviewId, Place place, String content, User user, Integer rewarded) {
        this.reviewId = reviewId;
//        this.placeId = placeId;
        this.place = place;
        this.content = content;
//        this.userId = userId;
        this.user = user;
        this.rewarded = rewarded;
    }
}
