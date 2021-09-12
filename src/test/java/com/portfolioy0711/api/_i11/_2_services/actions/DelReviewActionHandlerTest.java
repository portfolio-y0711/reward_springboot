package com.portfolioy0711.api._i11._2_services.actions;


import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.data.entities.*;
import com.portfolioy0711.api.data.models.photo.PhotoModel;
import com.portfolioy0711.api.data.models.place.PlaceModel;
import com.portfolioy0711.api.data.models.review.ReviewModel;
import com.portfolioy0711.api.data.models.reward.RewardModel;
import com.portfolioy0711.api.data.models.user.UserModel;
import com.portfolioy0711.api.services.review.actions.ModReviewActionHandler;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import com.portfolioy0711.api.typings.exception.NoRecordException;
import com.portfolioy0711.api.typings.response.ReviewResponse;
import com.portfolioy0711.api.typings.response.UserRewardReponse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootTest
public class DelReviewActionHandlerTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EventDatabase eventDatabase;


    @Test
    @Transactional
    public void delReviewActionHandlerTest() {
        final ModReviewActionHandler modReviewActionHandler = new ModReviewActionHandler(eventDatabase);

        final UserModel userModel = eventDatabase.getUserModel();
        final PlaceModel placeModel = eventDatabase.getPlaceModel();
        final ReviewModel reviewModel = eventDatabase.getReviewModel();
        final RewardModel rewardModel = eventDatabase.getRewardModel();
        final PhotoModel photoModel = eventDatabase.getPhotoModel();

        final User _user = userModel.save(
                User.builder()
                        .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                        .name("Michael")
                        .rewardPoint(3)
                        .build()
        );

        final Place _place = placeModel.save(
                Place.builder()
                        .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                        .name("멜번")
                        .country("호주")
                        .bonusPoint(0)
                        .build()
        );

        final Review _review = reviewModel.save(
            Review.builder()
                    .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
                    .content("좋아요")
                    .user(_user)
                    .place(_place)
                    .rewarded(1)
                    .build()
        );

        photoModel.save(
            Photo.builder()
                    .review(_review)
                    .photoId("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8")
                    .build());

        photoModel.save(
            Photo.builder()
                .review(_review)
                .photoId("afb0cef2-851d-4a50-bb07-9cc15cbdc332")
                .build());

        final UUID uuid = UUID.randomUUID();

        rewardModel.save(
            Reward
                .builder()
                .rewardId(uuid.toString())
                .reviewId(_review.getReviewId())
                .user(_user)
                .pointDelta(2)
                .operation("ADD")
                .reason("NEW")
                .build()
        );

        final ReviewEventDto eventInfo = ReviewEventDto
                .builder()
                .type("REVIEW")
                .action("DELETE")
                .content("좋아요")
                .attachedPhotoIds(new String[] {
                    "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
                    "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
                })
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .build();

//        modReviewActionHandler.handleEvent(eventInfo);

        logger.info(String.format("[EVENT: ReviewEventActionHandler (%s)] started process ========================START", eventInfo.getAction()));
//        final ReviewModel reviewModel = eventDatabase.getReviewModel();
        final ReviewResponse found = reviewModel.findReviewInfoByReviewId(eventInfo.getReviewId());

        if (found == null) {
            logger.error("\t‣" + "\tno record exists by that reviewId");
            throw new NoRecordException("no record exists by that reviewId");
        }

        final boolean isRewarded = found.getRewarded() == 1;

        logger.info(String.format("\t‣" + "︎\treview id : %s", eventInfo.getReviewId()));
        logger.info(String.format("\t‣" + "\tplace id: %s", eventInfo.getPlaceId()));
        logger.info(String.format("\t‣" + "\treview rewarded?: %s", isRewarded ? "YES" : "NO"));

        if (isRewarded) {
//            final RewardModel rewardModel = eventDatabase.getRewardModel();
            final UserRewardReponse latestRewardRecord = rewardModel.findLatestRewardByUserIdAndReviewId(eventInfo.getUserId(), eventInfo.getReviewId());

            logger.info(String.format("\t‣" + "\tsearch latest reward record with 'userId' and 'reviewId'"));
            logger.info(String.format("\t\t reviewId: %s", latestRewardRecord.getReviewId()));
            logger.info(String.format("\t\t reason: %s review", latestRewardRecord.getReason()));
            logger.info(String.format("\t\t operation: %s %s", latestRewardRecord.getOperation(), latestRewardRecord.getPointDelta()));

            final int deductPoint = -latestRewardRecord.getPointDelta();
            logger.info(String.format("\t‣" + "\tpoints to deduct: %s", deductPoint));

//            final UserModel userModel = eventDatabase.getUserModel();
            final int currPoint = userModel.findUserRewardPoint(eventInfo.getUserId());

            logger.info(String.format("\t\t user's current rewards point: %s", currPoint));
            logger.info(String.format("\t\t user's next rewards point: %s", currPoint + deductPoint));

            logger.info("\ttransaction started ------------------------------------BEGIN");

            final String subtract_operation = "SUB";
            final String subtract_reason = "DEL";

            final User user = userModel.findUserByUserId(eventInfo.getUserId());

            rewardModel.save(
                    Reward.builder()
                            .rewardId(UUID.randomUUID().toString())
                            .user(user)
                            .reviewId(eventInfo.getReviewId())
                            .reason(subtract_reason)
                            .operation(subtract_operation)
                            .pointDelta(currPoint)
                            .build()
            );

            logger.info(String.format("\t[✔︎] REWARDS %s record created", subtract_operation));

            userModel.updateRewardPoint(
                    eventInfo.getUserId(),
                    currPoint + deductPoint
            );
            logger.info("\t[✔︎] USERS total rewardPoint updated");
        }

        final ReviewResponse currentReview = reviewModel.findReviewInfoByReviewId(eventInfo.getReviewId());
        final String[] delete_photo_ids = currentReview.getPhotoIds().stream().toArray(String[]::new);

//        final PhotoModel photoModel = eventDatabase.getPhotoModel();

        Arrays.stream(delete_photo_ids)
                .forEach(photoModel::remove);

        logger.info("\t[✔︎] PHOTOS has been deleted");

        reviewModel.remove(eventInfo.getReviewId());

        logger.info("\t[✔︎] REVIEWS review has been deleted");
        logger.info("\ttransaction finished -------------------------------------END");

        logger.info("===================================================================================END");
    }
}
