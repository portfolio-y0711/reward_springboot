package com.portfolioy0711.api._i11._2_services.actions;


import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.data.entities.Place;
import com.portfolioy0711.api.data.entities.Review;
import com.portfolioy0711.api.data.entities.Reward;
import com.portfolioy0711.api.data.entities.User;
import com.portfolioy0711.api.data.models.PlaceModel;
import com.portfolioy0711.api.data.models.ReviewModel;
import com.portfolioy0711.api.data.models.RewardModel;
import com.portfolioy0711.api.data.models.UserModel;
import com.portfolioy0711.api.services.review.actions.AddReviewActionHandler;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import com.portfolioy0711.api.typings.vo.BooleanType;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
public class AddReviewActionHandlerTest {

    @Autowired
    AddReviewActionHandler addReviewActionHandler;

    @Autowired
    EventDatabase eventDatabase;

    @Before
    public void setUp() {
    }

    @Test
    @Transactional
    public void addReviewActionHandlerTest() {
        PlaceModel placeModel = eventDatabase.getPlaceModel();
        placeModel.save(
                Place.builder()
                        .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                        .name("멜번")
                        .country("호주")
                        .bonusPoint(0)
                        .build()
        );

        UserModel userModel = eventDatabase.getUserModel();
        userModel.save(
                User.builder()
                        .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                        .name("Michael")
                        .rewardPoint(3)
                        .build()
        );

        ReviewEventDto eventInfo = ReviewEventDto
                .builder()
                .type("REVIEW")
                .action("ADD")
                .content("좋아요")
                .attachedPhotoIds(new String[] {
                        "",""
                })
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .build();

        addReviewActionHandler.handleEvent(eventInfo);

        ReviewModel reviewModel = eventDatabase.getReviewModel();
        boolean isDuplicate = reviewModel.checkRecordExistsByReviewId(eventInfo.getReviewId());
        Integer reviewCount = reviewModel.findReviewCountsByPlaceId(eventInfo.getPlaceId());

        boolean isRewardable = (reviewCount == 0);

        if (isRewardable) {
            placeModel = eventDatabase.getPlaceModel();
            Place place = placeModel.findPlaceByPlaceId(eventInfo.getPlaceId());

            Integer bonusPoint = place.getBonusPoint();

            Integer contentPoint = eventInfo.getContent().length()  > 1 ? 1 : 0;
            Integer photosPoint = eventInfo.getAttachedPhotoIds().length > 1 ? 1 : 0;
            Integer addPoint = contentPoint + photosPoint + bonusPoint;

            userModel = eventDatabase.getUserModel();
            User user = userModel.findUserByUserId(eventInfo.getUserId());

            Integer currPoint = user.getRewardPoint();

            String addOperation = "ADD";
            String addReason = "NEW";

            RewardModel rewardModel = eventDatabase.getRewardModel();
            rewardModel.save(
                Reward
                    .builder()
                    .operation(addOperation)
                    .pointDelta(currPoint + addPoint)
                    .reason(addReason)
                    .rewardId(eventInfo.getReviewId())
                    .build()
            );

            reviewModel.save(
                Review
                    .builder()
                    .reviewId(eventInfo.getReviewId())
                    .content(eventInfo.getContent())
                    .rewarded(BooleanType.TRUE.getValue())
                    .user(user)
                    .place(place)
                    .build()
            );
        }
    }
}
