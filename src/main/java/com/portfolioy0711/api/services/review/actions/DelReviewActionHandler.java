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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DelReviewActionHandler implements ActionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EventDatabase eventDatabase;

    public DelReviewActionHandler(EventDatabase eventDatabase) {
        this.eventDatabase = eventDatabase;
    }

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

        logger.info(String.format("\t‣" + "︎\treview id : %s", eventInfo.getReviewId()));
        logger.info(String.format("\t‣" + "\tplace id: %s", eventInfo.getPlaceId()));
        logger.info(String.format("\t‣" + "\treview rewarded?: %s", isRewarded ? "YES" : "NO"));

        if (isRewarded) {
            final RewardModel rewardModel = eventDatabase.getRewardModel();
            final UserRewardReponse latestRewardRecord = rewardModel.findLatestRewardByUserIdAndReviewId(eventInfo.getUserId(), eventInfo.getReviewId());

            logger.info(String.format("\t‣" + "\tsearch latest reward record with 'userId' and 'reviewId'"));
            logger.info(String.format("\t\t reviewId: %s", latestRewardRecord.getReviewId()));
            logger.info(String.format("\t\t reason: %s review", latestRewardRecord.getReason()));
            logger.info(String.format("\t\t operation: %s %s", latestRewardRecord.getOperation(), latestRewardRecord.getPointDelta()));

            final int deductPoint = -latestRewardRecord.getPointDelta();
            logger.info(String.format("\t‣" + "\tpoints to deduct: %s", deductPoint));

            final UserModel userModel = eventDatabase.getUserModel();
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

        final PhotoModel photoModel = eventDatabase.getPhotoModel();

        Arrays.stream(delete_photo_ids)
                .forEach(photoModel::remove);

        logger.info("\t[✔︎] PHOTOS has been deleted");

        reviewModel.remove(eventInfo.getReviewId());

        logger.info("\t[✔︎] REVIEWS review has been deleted");
        logger.info("\ttransaction finished -------------------------------------END");

        logger.info("===================================================================================END");
    }
}
