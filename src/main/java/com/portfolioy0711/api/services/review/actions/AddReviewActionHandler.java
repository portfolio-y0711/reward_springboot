package com.portfolioy0711.api.services.review.actions;

import com.portfolioy0711.api.data.entities.*;
import com.portfolioy0711.api.data.models.photo.PhotoModel;
import com.portfolioy0711.api.data.models.place.PlaceModel;
import com.portfolioy0711.api.data.models.review.ReviewModel;
import com.portfolioy0711.api.data.models.reward.RewardModel;
import com.portfolioy0711.api.data.models.user.UserModel;
import com.portfolioy0711.api.typings.ActionHandler;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import com.portfolioy0711.api.typings.vo.BooleanType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.portfolioy0711.api.data.EventDatabase;

import javax.transaction.Transactional;
import java.util.*;


@Component
public class AddReviewActionHandler implements ActionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EventDatabase eventDatabase;

    @Transactional
    @Override
    public void handleEvent(Object event) {
        ReviewEventDto eventInfo = (ReviewEventDto) event;
        logger.info(String.format("[EVENT: ReviewEventActionHandler (%s)] started process ========================START", eventInfo.getAction()));
        ReviewModel reviewModel = eventDatabase.getReviewModel();

        boolean isDuplicate = reviewModel.checkRecordExistsByReviewId(eventInfo.getReviewId());

//        if (isDuplicate) {
//            throw new Exception();
//        }

        logger.error("\t‣" + "\tduplicate record exists by that reviewId");
        Integer reviewCount = reviewModel.findReviewCountsByPlaceId(eventInfo.getPlaceId());

        boolean isRewardable = (reviewCount == 0);

        logger.info(String.format("\t‣" +"︎\tplace id : %s", eventInfo.getPlaceId()));
        logger.info(String.format("\t‣" +"\treview counts: %s", reviewCount));
        logger.info(String.format("\t‣" +"\treview rewardable?: %s", isRewardable ? "YES": "NO"));


        if (isRewardable) {
            PlaceModel placeModel = eventDatabase.getPlaceModel();
            Place place = placeModel.findPlaceByPlaceId(eventInfo.getPlaceId());
            Integer bonusPoint = place.getBonusPoint();

            logger.info("\t‣" +"\tcalculate review points");
            logger.info(String.format("\t‣" +"\tbonusPoint: %s", bonusPoint));

            Integer contentPoint = eventInfo.getContent().length() > 1 ? 1 : 0;
            Integer photosPoint = eventInfo.getAttachedPhotoIds().length > 1 ? 1 : 0;
            Integer addPoint = contentPoint + photosPoint + bonusPoint;

            logger.info(String.format("\t\t+ content point: %s", contentPoint));
            logger.info(String.format("\t\t+ photos point: %s", photosPoint));
            logger.info(String.format("\t\t= total point : %s", addPoint));

            UserModel userModel = eventDatabase.getUserModel();
            User user = userModel.findUserByUserId(eventInfo.getUserId());

            Integer currPoint = userModel.findUserRewardPoint(eventInfo.getUserId());

            logger.info(String.format("\t‣" +"\tuser's current rewards point: %s", currPoint));
            logger.info(String.format("\t‣" +"\tuser's next rewards point: %s", currPoint + addPoint));

            String addOperation = "ADD";
            String addReason = "NEW";

            logger.info("\ttransaction started ------------------------------------BEGIN");
            Review review = reviewModel.save(
                    Review
                            .builder()
                            .reviewId(eventInfo.getReviewId())
                            .content(eventInfo.getContent())
                            .rewarded(BooleanType.TRUE.getValue())
                            .user(user)
                            .place(place)
                            .build()
            );

            logger.info("\t[✔︎] REVIEWS review has been created");

            place.getReviews().add(review);
            placeModel.save(place);

            UUID uuid = UUID.randomUUID();

            RewardModel rewardModel = eventDatabase.getRewardModel();

            rewardModel.save(
                Reward
                    .builder()
                    .rewardId(uuid.toString())
                    .operation(addOperation)
                    .pointDelta(currPoint + addPoint)
                    .reason(addReason)
                    .user(user)
                    .reviewId(eventInfo.getReviewId())
                    .build()
            );

            logger.info(String.format("\t[✔︎] REWARDS %s record created", addOperation));
            PhotoModel photoModel = eventDatabase.getPhotoModel();

            String[] photoIds = eventInfo.getAttachedPhotoIds();
            Arrays.stream(photoIds)
                    .map(photoId -> new Photo(photoId, review))
                    .forEach(photoModel::save);

            logger.info(String.format("\t[✔︎] %s PHOTOS created & attached", photoIds.length));

            userModel.updateRewardPoint(eventInfo.getUserId(), currPoint + addPoint);
            logger.info("\t[✔︎] USERS total reward point updated");
            logger.info("\ttransaction finished -------------------------------------END");
        }
        logger.info("===================================================================================END");
    }
}
