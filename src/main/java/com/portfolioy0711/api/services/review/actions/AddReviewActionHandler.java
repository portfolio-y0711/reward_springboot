package com.portfolioy0711.api.services.review.actions;

import com.portfolioy0711.api.data.entities.Review;
import com.portfolioy0711.api.data.entities.Reward;
import com.portfolioy0711.api.data.models.PlaceModel;
import com.portfolioy0711.api.data.models.ReviewModel;
import com.portfolioy0711.api.data.models.RewardModel;
import com.portfolioy0711.api.data.models.UserModel;
import com.portfolioy0711.api.typings.ActionHandler;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import com.portfolioy0711.api.typings.vo.ReviewActionType;
import com.portfolioy0711.api.typings.vo.RewardReasonType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.portfolioy0711.api.data.EventDatabase;

import javax.transaction.Transactional;


@Component
public class AddReviewActionHandler implements ActionHandler {

    @Autowired
    EventDatabase eventDatabase;

    @Transactional
    @Override
    public void handleEvent(Object event) {
//        ReviewEventDto eventInfo = (ReviewEventDto) event;
//        ReviewModel reviewModel = eventDatabase.getReviewModel();
//
//        boolean isDuplicate = reviewModel.checkRecordExistsByReviewId(eventInfo.getReviewId());
//        Integer reviewCount = reviewModel.findReviewCountsByPlaceId(eventInfo.getPlaceId());
//
//        boolean isRewardable = (reviewCount == 0);
//
//        if (isRewardable) {
//            PlaceModel placeModel = eventDatabase.getPlaceModel();
//            Integer bonusPoint = placeModel.findBonusPoint(eventInfo.getPlaceId());
//
//            Integer contentPoint = eventInfo.getContent().length() > 1 ? 1 : 0;
//            Integer photosPoint = eventInfo.getAttachedPhotoIds().length > 1 ? 1 : 0;
//            Integer addPoint = contentPoint + photosPoint + bonusPoint;
//
//            UserModel userModel = eventDatabase.getUserModel();
//            Integer currPoint = userModel.findUserRewardPoint(eventInfo.getUserId());
//
//            String addOperation = ReviewActionType.ADD.name();
//            String addReason = RewardReasonType.NEW.name();
//
//            RewardModel rewardModel = eventDatabase.getRewardModel();
//
//            rewardModel.save(
//                Reward
//                    .builder()
//                    .operation("SUB")
//                    .pointDelta(addPoint)
//                    .reason(ReviewActionType.ADD.name())
//                    .rewardId(eventInfo.getReviewId())
//                    .build()
//            );
//
//            reviewModel.save(
//                Review
//                    .builder()
//                    .reviewId(eventInfo.getReviewId())
//                    .content(eventInfo.getContent())
//                    .rewarded(1)
////                    .userId(eventInfo.getUserId())
////                    .placeId(eventInfo.getPlaceId())
//                    .build()
//            );
//
//            userModel.updateRewardPoint(eventInfo.getUserId(), currPoint + addPoint);
//        }
    }
}
