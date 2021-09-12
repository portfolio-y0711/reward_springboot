package com.portfolioy0711.api._i11._1_databases;

import com.portfolioy0711.api.data.entities.Photo;
import com.portfolioy0711.api.data.entities.Place;
import com.portfolioy0711.api.data.entities.Review;
import com.portfolioy0711.api.data.entities.User;
import com.portfolioy0711.api.data.models.photo.PhotoModel;
import com.portfolioy0711.api.data.models.place.PlaceModel;
import com.portfolioy0711.api.data.models.review.ReviewModel;
import com.portfolioy0711.api.data.models.user.UserModel;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest
public class PhotoModelTest {

    @Autowired
    private JPAQueryFactory query;

    @Autowired
    UserModel userModel;

    @Autowired
    PhotoModel photoModel;

    @Autowired
    PlaceModel placeModel;

    @Autowired
    ReviewModel reviewModel;

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
            .content("좋아요`")
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
        assertArrayEquals(photoModel.findPhotos().toArray(), new Photo[] { new Photo("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", review), new Photo("afb0cef2-851d-4a50-bb07-9cc15cbdc332", review) } );
    }

    @Test
    @Transactional
    public void findPhotoByPhotoIdTest() {
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
                .content("좋아요`")
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
        assertArrayEquals(photoModel.findPhotos().toArray(), new Photo[] { new Photo("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", review), new Photo("afb0cef2-851d-4a50-bb07-9cc15cbdc332", review) } );
    }
}
