package com.portfolioy0711.api._i11._2_services.actions;


import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.data.entities.Place;
import com.portfolioy0711.api.data.entities.Review;
import com.portfolioy0711.api.data.entities.Reward;
import com.portfolioy0711.api.data.entities.User;
import com.portfolioy0711.api.data.models.photo.PhotoModel;
import com.portfolioy0711.api.data.models.place.PlaceModel;
import com.portfolioy0711.api.data.models.review.ReviewModel;
import com.portfolioy0711.api.data.models.reward.RewardModel;
import com.portfolioy0711.api.data.models.user.UserModel;
import com.portfolioy0711.api.services.review.actions.AddReviewActionHandler;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import com.portfolioy0711.api.typings.vo.BooleanType;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AddReviewActionHandlerTest {

    @Autowired
    private AddReviewActionHandler addReviewActionHandler;

    @Autowired
    private EventDatabase eventDatabase;

    @Before
    public void setUp() {
    }

    @Test
    @Transactional
    public void addReviewActionHandlerTest() {
        UserModel userModel = eventDatabase.getUserModel();
        PlaceModel placeModel = eventDatabase.getPlaceModel();

        userModel.save(
                User.builder()
                        .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                        .name("Michael")
                        .rewardPoint(3)
                        .build()
        );

        placeModel.save(
                Place.builder()
                        .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                        .name("멜번")
                        .country("호주")
                        .bonusPoint(0)
                        .build()
        );

        ReviewEventDto eventInfo = ReviewEventDto
                .builder()
                .type("REVIEW")
                .action("ADD")
                .content("좋아요")
                .attachedPhotoIds(new String[] {
                    "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
                    "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
                })
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .build();

        addReviewActionHandler.handleEvent(eventInfo);

//        assertEquals();

    }
}
