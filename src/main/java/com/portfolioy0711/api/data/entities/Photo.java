package com.portfolioy0711.api.data.entities;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@ToString(of = { "photoId" } )
public class Photo {
    @Id
    private String photoId;

    @ManyToOne(targetEntity = Review.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;

    @Builder
    public Photo(String photoId, Review review) {
        this.photoId = photoId;
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return photoId.equals(photo.photoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(photoId);
    }
}
