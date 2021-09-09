package com.portfolioy0711.api.data.models;

import com.portfolioy0711.api.data.entities.QPlace;
import com.portfolioy0711.api.data.entities.QReview;
import com.portfolioy0711.api.data.entities.QUser;
import com.portfolioy0711.api.data.entities.Review;
import com.portfolioy0711.api.data.models.review.ReviewCmdRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
        QReview review = QReview.review;
        long result = query.from(review)
                .where(review.reviewId.eq(reviewId))
                .fetchCount();
       return (result > 0) ? true : false;
    }

    public Integer findReviewCountsByPlaceId(String placeId) {
       return 0;
    }

    public List<Review> findReviews() {
        QReview review = QReview.review;
        return query.selectFrom(review)
                .fetch();
    }

    public List<Review> findReviewsByUserId(String userId) {
        QReview review = QReview.review;
        QUser user = QUser.user;

        List<Review> result = query.select(review)
                .from(review)
                .join(user)
                .on(review.user().userId.eq(user.userId))
                .where(review.user().userId.eq(userId))
//                .join(review.user(), user)
//                .where(review.user().userId.eq(userId))
                .fetch();
        return result;
    }

    public List<Review> findReviewsByPlaceId(String placeId) {
        QReview review = QReview.review;
        QPlace place = QPlace.place;

        List<Review> result = query.select(review)
                .from(review)
//                .join(place)
//                .on(review.place().placeId.eq(place.placeId))
//                .where(review.place().placeId.eq(placeId))
                .join(review.place(), place)
                .where(review.place().placeId.eq(placeId))
                .fetch();
        return result;
    }

    public List<Tuple> findReviewsByUserIdAndPlaceId(String userId, String placeId) {
        QReview review = QReview.review;
        QPlace place = QPlace.place;
        QUser user = QUser.user;

        List<Tuple> result = query.select()
                .from(review)
                .join(review.place(), place)
                .join(review.user(), user)
//                .where(review.place().placeId.eq(placeId), review.user().userId.eq(userId))
                .fetch();
       return result;
    }

    public void findReviewsByReviewId(String reviewId) {
//        QReview review = QReview.review;
//        query.from(review)
//                .where()
//                .select(new QReviewResponse(review.reviewId, review.place().placeId, review.user().userId, review.content, review.rewarded, review))
//        reviewId, placeId, userId, content, rewarded, attachedPhotoIds
//
//        this.reviewId = reviewId;
//        this.placeId = placeId;
//        this.userId = userId;
//        this.content = content;
//        this.rewarded = rewarded;
//        this.attachedPhotoIds = attachedPhotoIds;
//                new ReviewResponse();
    }

    public Review save(Review review) {
        return userCmdRepository.save(review);
    }
}
