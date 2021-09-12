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
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class ModReviewActionHandlerTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EventDatabase eventDatabase;


    @Test
    @Transactional
    public void modReviewActionHandlerTest() {
        ModReviewActionHandler modReviewActionHandler = new ModReviewActionHandler(eventDatabase);

        UserModel userModel = eventDatabase.getUserModel();
        PlaceModel placeModel = eventDatabase.getPlaceModel();
        ReviewModel reviewModel = eventDatabase.getReviewModel();
        RewardModel rewardModel = eventDatabase.getRewardModel();
        PhotoModel photoModel = eventDatabase.getPhotoModel();

        User _user = userModel.save(
                User.builder()
                        .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                        .name("Michael")
                        .rewardPoint(3)
                        .build()
        );

        Place _place = placeModel.save(
                Place.builder()
                        .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                        .name("멜번")
                        .country("호주")
                        .bonusPoint(0)
                        .build()
        );


        Review _review = reviewModel.save(
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

        UUID uuid = UUID.randomUUID();

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

        ReviewEventDto eventInfo = ReviewEventDto
                .builder()
                .type("REVIEW")
                .action("MOD")
                .content("좋아요")
                .attachedPhotoIds(new String[] {
                    "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
//                    "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
                })
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .build();

//        modReviewActionHandler.handleEvent(eventInfo);
//

        logger.info(String.format("[EVENT: ReviewEventActionHandler (%s)] started process ========================START", eventInfo.getAction()));
        reviewModel = eventDatabase.getReviewModel();
        ReviewResponse found = reviewModel.findReviewInfoByReviewId(eventInfo.getReviewId());

        if (found == null) {
            logger.error("\t‣" + "\tno record exists by that reviewId");
            throw new NoRecordException("no record exists by that reviewId");
        }

        boolean isRewarded = found.getRewarded() == 1;

        logger.info(String.format("\t‣" +"︎\treview id : %s", eventInfo.getReviewId()));
        logger.info(String.format("\t‣" +"\tplace id: %s", eventInfo.getPlaceId()));
        logger.info(String.format("\t‣" +"\treview rewarded?: %s", isRewarded ? "YES": "NO"));

        if (isRewarded) {
            UserRewardReponse latestRewardRecord = rewardModel.findLatestRewardByUserIdAndReviewId(eventInfo.getUserId(), eventInfo.getReviewId());

            logger.info(String.format("\t‣" +"\tsearch latest reward record with 'userId' and 'reviewId'"));
            logger.info(String.format("\t\t reviewId: %s", latestRewardRecord.getReviewId()));
            logger.info(String.format("\t\t reason: %s review", latestRewardRecord.getReason()));
            logger.info(String.format("\t\t operation: %s %s", latestRewardRecord.getOperation(), latestRewardRecord.getPointDelta()));

            Place place = placeModel.findPlaceByPlaceId(eventInfo.getPlaceId());
            Integer bonusPoint = place.getBonusPoint();

            logger.info(String.format("\t‣" +"\tbonusPoint: %s", bonusPoint));

            Integer contentPoint = eventInfo.getContent().length() > 1 ? 1 : 0;
            Integer photosPoint = eventInfo.getAttachedPhotoIds().length > 1 ? 1 : 0;
            Integer totalPoint = contentPoint + photosPoint + bonusPoint;

            logger.info(String.format("\t\t+ content point: %s", contentPoint));
            logger.info(String.format("\t\t+ photos point: %s", photosPoint));
            logger.info(String.format("\t\t= total point : %s", totalPoint));

            int diff = totalPoint - latestRewardRecord.getPointDelta();
            logger.info(String.format("\t‣" +"\tpoint diff: %s", diff));

            if (diff == 0) {
                logger.info("\ttransaction started ------------------------------------BEGIN");
            } else {
                String subtract_operation = "SUB";
                String subtract_reason = "MOD";

                String add_operation = "ADD";
                String add_reason = "MOD";

                User user = userModel.findUserByUserId(eventInfo.getUserId());
                int currPoint = user.getRewardPoint();

                logger.info(String.format("\t‣" +"\tuser's current rewardPoint: %s", currPoint));
                logger.info(String.format("\t‣" +"\tuser's next rewardPoint: %s", currPoint + diff));

                logger.info("\ttransaction started ------------------------------------BEGIN");

                rewardModel.save(
                    Reward.builder()
                        .rewardId(UUID.randomUUID().toString())
                        .user(_user)
                        .reason(subtract_reason)
                        .operation(subtract_operation)
                        .pointDelta(currPoint)
                        .build()
                );

                rewardModel.save(
                    Reward.builder()
                        .rewardId(UUID.randomUUID().toString())
                        .user(_user)
                        .reason(add_reason)
                        .operation(add_operation)
                        .pointDelta(totalPoint)
                        .build()
                );

                logger.info(String.format("\t[✔︎] REWARDS %s record created", subtract_operation));
                logger.info(String.format("\t[✔︎] REWARDS %s record created", add_operation));

                userModel.updateRewardPoint(
                    eventInfo.getUserId(),
         currPoint + diff
                );
                logger.info("\t[✔︎] USERS total rewardPoint updated");
            }

            ReviewResponse currentReview = reviewModel.findReviewInfoByReviewId(eventInfo.getReviewId());

            ArrayList<String> currentPhotoIds = (ArrayList<String>) currentReview.getPhotoIds().stream().collect(Collectors.toList());
            ArrayList<String> newPhotoIds = (ArrayList<String>) Arrays.stream(eventInfo.getAttachedPhotoIds()).collect(Collectors.toList());

            String[] add_photo_ids = Arrays.stream(eventInfo.getAttachedPhotoIds())
                    .filter(photoId -> !currentPhotoIds.contains(photoId))
                    .toArray(String[]::new);

            String[] delete_photo_ids = currentPhotoIds.stream()
                    .filter(currentPhotoId -> !newPhotoIds.contains(currentPhotoId))
                    .toArray(String[]::new);

            if (add_photo_ids.length > 0 || delete_photo_ids.length > 0) {
                Review review = reviewModel.findReviewByReviewId(eventInfo.getReviewId());

                Arrays.stream(add_photo_ids)
                        .map(photoId -> new Photo(photoId, review))
                        .forEach(photoModel::save);

                Arrays.stream(delete_photo_ids)
                        .forEach(photoModel::remove);

                logger.info("\t[✔︎] PHOTOS has been updated");
            }

            reviewModel.updateReview(
                    eventInfo.getReviewId(),
                    eventInfo.getContent()
            );

            logger.info("\t[✔︎] REVIEWS review has been updated");
            logger.info("\ttransaction finished -------------------------------------END");
        }
        logger.info("===================================================================================END");
    }
}
