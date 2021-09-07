package com.portfolioy0711.api.data.models;

import com.portfolioy0711.api.data.entities.QReview;
import com.portfolioy0711.api.data.entities.Review;
import com.portfolioy0711.api.data.models.review.ReviewCmdRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewModel {
    @Autowired
    ReviewCmdRepository userCmdRepository;

    @Autowired
    private JPAQueryFactory query;

    public boolean checkRecordExistsByReviewId(String reviewId) {
        QReview qReview = QReview.review;
        long result = query.from(qReview)
                .where(qReview.reviewId.eq(reviewId))
                .fetchCount();
       return (result > 0) ? true : false;
    }

    public Integer findReviewCountsByPlaceId(String placeId) {
       return 0;
    }

    public Review save(Review review) {
        return userCmdRepository.save(review);
    }
}
