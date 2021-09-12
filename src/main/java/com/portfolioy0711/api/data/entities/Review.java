package com.portfolioy0711.api.data.entities;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(of = { "reviewId", "content", "rewarded" } )
public class Review extends Base {
    @Id
    private String reviewId;

    @ManyToOne(targetEntity = Place.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "placeId", nullable = false)
    private Place place;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer rewarded;

    @OneToMany(mappedBy = "review")
    private Set<Photo> photos = new HashSet<>();

    @Builder
    public Review(String reviewId, Place place, User user, String content, Integer rewarded, Set<Photo> photos) {
        this.reviewId = reviewId;
        this.place = place;
        this.user = user;
        this.content = content;
        this.rewarded = rewarded;
        this.photos = photos;
    }
}
