package com.portfolioy0711.api.data.models;

import com.portfolioy0711.api.data.entities.Review;
import com.portfolioy0711.api.data.models.review.ReviewCmdRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewModel {
    ReviewCmdRepository userCmdRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    public Boolean checkRecordExistsByReviewId(String reviewId) {
       return true;
    }

    public Integer findReviewCountsByPlaceId(String placeId) {
       return 0;
    }

    public Review save(Review review) {
        return userCmdRepository.save(review);
    }
}
