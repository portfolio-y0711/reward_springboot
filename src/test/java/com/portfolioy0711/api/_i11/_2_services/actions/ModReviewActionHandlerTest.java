package com.portfolioy0711.api._i11._2_services.actions;


import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.data.entities.*;
import com.portfolioy0711.api.data.models.photo.PhotoModel;
import com.portfolioy0711.api.data.models.place.PlaceModel;
import com.portfolioy0711.api.data.models.review.ReviewModel;
import com.portfolioy0711.api.data.models.reward.RewardModel;
import com.portfolioy0711.api.data.models.user.UserModel;
import com.portfolioy0711.api.services.review.actions.AddReviewActionHandler;
import com.portfolioy0711.api.services.review.actions.ModReviewActionHandler;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import com.portfolioy0711.api.typings.exception.DuplicateRecordException;
import com.portfolioy0711.api.typings.exception.NoRecordException;
import com.portfolioy0711.api.typings.response.ReviewResponse;
import com.portfolioy0711.api.typings.response.UserRewardReponse;
import com.portfolioy0711.api.typings.vo.BooleanType;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

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
        System.out.println(uuid);

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
                    "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
                })
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .build();

//        modReviewActionHandler.handleEvent(eventInfo);

        logger.info(String.format("[EVENT: ReviewEventActionHandler (%s)] started process ========================START", eventInfo.getAction()));
        reviewModel = eventDatabase.getReviewModel();
        ReviewResponse found = reviewModel.findReviewsByReviewId(eventInfo.getReviewId());

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

            if (diff != 0) {
                logger.info(String.format("\t‣" +"\tpoint diff: %s", totalPoint));
                String subtract_operation = "SUB";
                String subtract_reason = "MOD";

                String add_operation = "SUB";
                String add_reason = "MOD";

                User user = userModel.findUserByUserId(eventInfo.getUserId());
                int currPoint = user.getRewardPoint();

                logger.info("\ttransaction started ------------------------------------BEGIN");

                logger.info(String.format("\t‣" +"\tuser's current rewardPoint: %s", currPoint));
                logger.info(String.format("\t‣" +"\tuser's next rewardPoint: %s", currPoint + diff));

                rewardModel.save(
                    Reward.builder()
                            .build()
                );

                rewardModel.save(
                    Reward.builder()
                        .build()
                );

                userModel.updateRewardPoint(
                    eventInfo.getUserId(),
         currPoint + diff
                );

//                eventInfo.getAttachedPhotoIds()
//                photoModel.findPhotoByPhotoId();

//                reviewModel.updateReview(
//                    eventInfo.getReviewId(),
//                    eventInfo.getContent(),
//                );

            }

//            Review review = reviewModel.save(
//                    Review
//                            .builder()
//                            .reviewId(eventInfo.getReviewId())
//                            .content(eventInfo.getContent())
//                            .rewarded(BooleanType.TRUE.getValue())
//                            .user(user)
//                            .place(place)
//                            .build()
//            );
//
//            logger.info("\t[✔︎] REVIEWS review has been created");
//
//            place.getReviews().add(review);
//            placeModel.save(place);
//
//            UUID uuid = UUID.randomUUID();
//
//            RewardModel rewardModel = eventDatabase.getRewardModel();
//
//            rewardModel.save(
//                    Reward
//                            .builder()
//                            .rewardId(uuid.toString())
//                            .operation(addOperation)
//                            .pointDelta(currPoint + addPoint)
//                            .reason(addReason)
//                            .user(user)
//                            .reviewId(eventInfo.getReviewId())
//                            .build()
//            );
//
//            logger.info(String.format("\t[✔︎] REWARDS %s record created", addOperation));
//            PhotoModel photoModel = eventDatabase.getPhotoModel();
//
//            String[] photoIds = eventInfo.getAttachedPhotoIds();
//            Arrays.stream(photoIds)
//                    .map(photoId -> new Photo(photoId, review))
//                    .forEach(photoModel::save);
//
//            logger.info(String.format("\t[✔︎] %s PHOTOS created & attached", photoIds.length));
//            System.out.println(eventInfo.getUserId());
//
//            System.out.println(currPoint + addPoint);
//
//            userModel.updateRewardPoint(eventInfo.getUserId(), currPoint + addPoint);
//            logger.info("\t[✔︎] USERS total reward point updated");
//            logger.info("\ttransaction finished -------------------------------------END");
        }
//        logger.info("===================================================================================END");
    }
}
