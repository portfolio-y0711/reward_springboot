package com.portfolioy0711.api.data.models.review;

import com.portfolioy0711.api.data.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ReviewCmdRepository extends JpaRepository<Review, String> {
    @Query(value = "SELECT r.reviewId as reviewId, p.placeId as placeId, u.userId as userId, r.content as content, r.rewarded as rewarded, ph.photoId as photoId FROM Review r INNER JOIN Place p on r.place.placeId = p.placeId INNER JOIN User u on r.user.userId = u.userId LEFT JOIN Photo ph on ph.review.reviewId = r.reviewId where u.userId = ?1")
    List<Object[]> findReviewInfoByReviewId_native (String userId);
}
