package com.portfolioy0711.api.data.models.review;

import com.portfolioy0711.api.data.entities.*;
import com.portfolioy0711.api.typings.response.ReviewResponse;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static com.querydsl.core.group.GroupBy.groupBy;

@Component
@RequiredArgsConstructor
public class ReviewModel {
    private final ReviewCmdRepository userCmdRepository;
    private final JPAQueryFactory query;

    public boolean checkRecordExistsByReviewId(String reviewId) {
        final QReview review = QReview.review;
        long result = query.from(review)
                .where(review.reviewId.eq(reviewId))
                .fetchCount();
       return result > 0;
    }

    public Integer findReviewCountsByPlaceId(String placeId) {
       return 0;
    }

    public List<Review> findReviews() {
        final QReview review = QReview.review;
        return query.selectFrom(review)
                .fetch();
    }

    public void updateReview(String reviewId, String content, Set<Photo> photos) {
        final QReview review = QReview.review;
        query.update(review)
            .set(review.content, content)
            .set(review.photos, photos)
            .where(review.reviewId.eq(reviewId))
            .execute();

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
        final QReview review = QReview.review;
        final QPlace place = QPlace.place;
        final QUser user = QUser.user;
        final QPhoto photo = QPhoto.photo;

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
        final QReview review = QReview.review;
        final QPlace place = QPlace.place;
        final QUser user = QUser.user;
        final QPhoto photo = QPhoto.photo;

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
        final QReview review = QReview.review;
        final QPlace place = QPlace.place;
        final QUser user = QUser.user;
        final QPhoto photo = QPhoto.photo;

        ReviewResponse found;
        try {
            found = query.select(review, place, user)
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
        } catch (IndexOutOfBoundsException ex) {
           return null;
        }
        return found;
    }

    public Review save(Review review) {
        return userCmdRepository.save(review);
    }
}
