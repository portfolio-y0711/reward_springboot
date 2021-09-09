package com.portfolioy0711.api._i11._1_databases;

import com.portfolioy0711.api.data.entities.*;
import com.portfolioy0711.api.data.models.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class ReviewModelTest {

    @Autowired
    private JPAQueryFactory query;

    @Autowired
    UserModel userModel;

    @Autowired
    RewardModel rewardModel;

    @Autowired
    ReviewModel reviewModel;

    @Autowired
    PlaceModel placeModel;

    @Autowired
    PhotoModel photoModel;

    @Test
    @Transactional
    public void saveTest() {
        Place place = Place.builder()
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .country("호주")
                .name("멜번")
                .bonusPoint(1)
                .build();

        placeModel.save(place);

        User user = User.builder()
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .name("Michael")
                .rewardPoint(0)
                .build();

        userModel.save(user);

        Review review = Review.builder()
                .rewarded(1)
                .content("좋아요")
                .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
                .place(place)
                .user(user)
                .build();

        reviewModel.save(review);

        Photo photo1 = Photo.builder()
                .photoId("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8")
                .review(review)
                .build();
        photoModel.save(photo1);

        Photo photo2 = Photo.builder()
                .photoId("afb0cef2-851d-4a50-bb07-9cc15cbdc332")
                .review(review)
                .build();
        photoModel.save(photo2);


//        Review expected = Review.builder()
//                .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
//                .place(place)
//                .content("좋아요")
//                .rewarded(1)
//                .user(user)
//                .photos(Stream.of(photo1, photo2).collect(Collectors.toCollection(HashSet::new)))
//                .build();
//        reviewModel.save(expected);

//        System.out.println(reviewModel.findReviews());

//        System.out.println(
//            reviewModel.findReviewsByUserId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
//        );

        System.out.println(
            reviewModel.findReviewsByPlaceId(place.getPlaceId())
        );
//        reviewModel.findReviewsByUserIdAndPlaceId(user.getUserId(), place.getPlaceId());
    }
}
