package com.portfolioy0711.api.services.review.actions;

import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.data.entities.*;
import com.portfolioy0711.api.data.models.photo.PhotoModel;
import com.portfolioy0711.api.data.models.place.PlaceModel;
import com.portfolioy0711.api.data.models.review.ReviewModel;
import com.portfolioy0711.api.data.models.reward.RewardModel;
import com.portfolioy0711.api.data.models.user.UserModel;
import com.portfolioy0711.api.typings.ActionHandler;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import com.portfolioy0711.api.typings.exception.NoRecordException;
import com.portfolioy0711.api.typings.response.ReviewResponse;
import com.portfolioy0711.api.typings.response.UserRewardReponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ModReviewActionHandler implements ActionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EventDatabase eventDatabase;

    public ModReviewActionHandler(EventDatabase eventDatabase) {
        this.eventDatabase = eventDatabase;
    }

    @Transactional
    @Override
    public void handleEvent(Object event) {
        final ReviewEventDto eventInfo = (ReviewEventDto) event;

        logger.info(String.format("[EVENT: ReviewEventActionHandler (%s)] started process ========================START", eventInfo.getAction()));
        final ReviewModel reviewModel = eventDatabase.getReviewModel();
        final ReviewResponse found = reviewModel.findReviewInfoByReviewId(eventInfo.getReviewId());

        if (found == null) {
            logger.error("\t‣" + "\tno record exists by that reviewId");
            throw new NoRecordException("no record exists by that reviewId");
        }

        final boolean isRewarded = found.getRewarded() == 1;

        logger.info(String.format("\t‣" +"︎\treview id : %s", eventInfo.getReviewId()));
        logger.info(String.format("\t‣" +"\tplace id: %s", eventInfo.getPlaceId()));
        logger.info(String.format("\t‣" +"\treview rewarded?: %s", isRewarded ? "YES": "NO"));

        if (isRewarded) {
            final RewardModel rewardModel = eventDatabase.getRewardModel();
            final UserRewardReponse latestRewardRecord = rewardModel.findLatestRewardByUserIdAndReviewId(eventInfo.getUserId(), eventInfo.getReviewId());

            logger.info(String.format("\t‣" +"\tsearch latest reward record with 'userId' and 'reviewId'"));
            logger.info(String.format("\t\t reviewId: %s", latestRewardRecord.getReviewId()));
            logger.info(String.format("\t\t reason: %s review", latestRewardRecord.getReason()));
            logger.info(String.format("\t\t operation: %s %s", latestRewardRecord.getOperation(), latestRewardRecord.getPointDelta()));

            final PlaceModel placeModel = eventDatabase.getPlaceModel();
            final Place place = placeModel.findPlaceByPlaceId(eventInfo.getPlaceId());
            final Integer bonusPoint = place.getBonusPoint();

            logger.info(String.format("\t‣" +"\tbonusPoint: %s", bonusPoint));

            final int contentPoint = eventInfo.getContent().length() > 1 ? 1 : 0;
            final int photosPoint = eventInfo.getAttachedPhotoIds().length > 1 ? 1 : 0;
            final int totalPoint = contentPoint + photosPoint + bonusPoint;

            logger.info(String.format("\t\t+ content point: %s", contentPoint));
            logger.info(String.format("\t\t+ photos point: %s", photosPoint));
            logger.info(String.format("\t\t= total point : %s", totalPoint));

            int diff = totalPoint - latestRewardRecord.getPointDelta();
            logger.info(String.format("\t‣" +"\tpoint diff: %s", diff));

            if (diff == 0) {
                logger.info("\ttransaction started ------------------------------------BEGIN");
            } else {
                final String subtract_operation = "SUB";
                final String subtract_reason = "MOD";

                final String add_operation = "ADD";
                final String add_reason = "MOD";

                final UserModel userModel = eventDatabase.getUserModel();
                final User user = userModel.findUserByUserId(eventInfo.getUserId());
                final int currPoint = user.getRewardPoint();

                logger.info(String.format("\t‣" +"\tuser's current rewardPoint: %s", currPoint));
                logger.info(String.format("\t‣" +"\tuser's next rewardPoint: %s", currPoint + diff));

                logger.info("\ttransaction started ------------------------------------BEGIN");

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

                rewardModel.save(
                        Reward.builder()
                                .rewardId(UUID.randomUUID().toString())
                                .reviewId(eventInfo.getReviewId())
                                .user(user)
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

            final ReviewResponse currentReview = reviewModel.findReviewInfoByReviewId(eventInfo.getReviewId());

            final ArrayList<String> currentPhotoIds = (ArrayList<String>) currentReview.getPhotoIds().stream().collect(Collectors.toList());
            final ArrayList<String> newPhotoIds = (ArrayList<String>) Arrays.stream(eventInfo.getAttachedPhotoIds()).collect(Collectors.toList());

            final String[] add_photo_ids = Arrays.stream(eventInfo.getAttachedPhotoIds())
                    .filter(photoId -> !currentPhotoIds.contains(photoId))
                    .toArray(String[]::new);

            final String[] delete_photo_ids = currentPhotoIds.stream()
                    .filter(currentPhotoId -> !newPhotoIds.contains(currentPhotoId))
                    .toArray(String[]::new);

            final PhotoModel photoModel = eventDatabase.getPhotoModel();

            if (add_photo_ids.length > 0 || delete_photo_ids.length > 0) {
                final Review review = reviewModel.findReviewByReviewId(eventInfo.getReviewId());

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
