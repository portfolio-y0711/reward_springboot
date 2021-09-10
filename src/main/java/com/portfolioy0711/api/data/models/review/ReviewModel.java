package com.portfolioy0711.api.data.models.review;

import com.portfolioy0711.api.data.entities.*;
import com.portfolioy0711.api.typings.response.ReviewResponse;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;

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

//    public List<Review> findReviewsByPlaceId(String placeId) {
//        QReview review = QReview.review;
//        QPlace place = QPlace.place;
//
//           return query.select(review)
//                .from(review)
////                .join(place)
////                .on(review.place().placeId.eq(place.placeId))
////                .where(review.place().placeId.eq(placeId))
//                .join(review.place(), place)
//                .where(review.place().placeId.eq(placeId))
//                .fetch();
//    }

    public List<ReviewResponse> findReviewsByUserIdAndPlaceId(String userId, String placeId) {
        QReview review = QReview.review;
        QPlace place = QPlace.place;
        QUser user = QUser.user;
        QPhoto photo = QPhoto.photo;

        return query.select(review, place, user)
            .from(review)
            .join(review.place(), place)
            .join(review.user(), user)
            .join(review.photos, photo)
            .transform(
                    groupBy(review.reviewId)
                    .list(Projections.constructor(
                            ReviewResponse.class,
                            review.reviewId,
                            place.placeId,
                            user.userId,
                            review.content,
                            review.rewarded,
                            GroupBy.set(photo.photoId)
                    ))
            );
    }

    public List<ReviewResponse> findReviewsByUserId(String userId) {
        QReview review = QReview.review;
        QPlace place = QPlace.place;
        QUser user = QUser.user;
        QPhoto photo = QPhoto.photo;

        return query.select(review, place, user)
                .from(review)
                .where(review.user().userId.eq(userId))
                .join(review.place(), place)
                .join(review.user(), user)
                .join(review.photos, photo)
                .transform(
                        groupBy(review.reviewId)
                                .list(Projections.constructor(
                                        ReviewResponse.class,
                                        review.reviewId,
                                        place.placeId,
                                        user.userId,
                                        review.content,
                                        review.rewarded,
                                        GroupBy.set(photo.photoId)
                                ))
                );
    }

    public ReviewResponse findReviewsByReviewId(String reviewId) {
        QReview review = QReview.review;
        QPlace place = QPlace.place;
        QUser user = QUser.user;
        QPhoto photo = QPhoto.photo;

        return query.select(review, place, user)
                .from(review)
                .where(review.reviewId.eq(reviewId))
                .join(review.place(), place)
                .join(review.user(), user)
                .join(review.photos, photo)
                .transform(
                        groupBy(review.reviewId)
                                .list(Projections.constructor(
                                        ReviewResponse.class,
                                        review.reviewId,
                                        place.placeId,
                                        user.userId,
                                        review.content,
                                        review.rewarded,
                                        GroupBy.set(photo.photoId)
                                ))
                ).get(0);
    }

    public Review save(Review review) {
        return userCmdRepository.save(review);
    }
}
