package com.portfolioy0711.api.data.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(of = { "reviewId", "content", "rewarded" } )
public class Review {
    @Id
    String reviewId;

    @ManyToOne(targetEntity = Place.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "placeId")
    Place place;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    User user;

    String content;
    Integer rewarded;

    @Builder
    public Review(String reviewId, Place place, String content, User user, Integer rewarded) {
        this.reviewId = reviewId;
        this.place = place;
        this.content = content;
        this.user = user;
        this.rewarded = rewarded;
    }
}
