package com.portfolioy0711.api.data.models;

import com.portfolioy0711.api.data.entities.QReward;
import com.portfolioy0711.api.data.entities.QUser;
import com.portfolioy0711.api.data.entities.Reward;
import com.portfolioy0711.api.data.models.reward.RewardCmdRepository;
import com.portfolioy0711.api.typings.response.QUserRewardReponse;
import com.portfolioy0711.api.typings.response.UserRewardReponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RewardModel {

    @Autowired
    RewardCmdRepository rewardCmdRepository;

    @Autowired
    private JPAQueryFactory query;

    public Reward save(Reward reward) {
        return rewardCmdRepository.save(reward);
    }

    public UserRewardReponse findLatestUserReviewRewardByReviewId(String userId, String reviewId) {
        QUser user = QUser.user;
        QReward reward = QReward.reward;
        return query.select(new QUserRewardReponse(reward.rewardId, reward.user().userId, reward.reviewId, reward.operation, reward.pointDelta, reward.reason))
                .from(reward)
                .join(reward.user(), user)
                .where(reward.user().userId.eq(userId))
                .where(reward.reviewId.eq(reviewId))
                .fetchOne();
    }

    public List<UserRewardReponse> findRewardsByUserId(String userId) {
        QReward reward = QReward.reward;
        QUser user = QUser.user;

        return query.select(new QUserRewardReponse(reward.rewardId, reward.user().userId, reward.reviewId, reward.operation, reward.pointDelta, reward.reason))
                .from(reward)
                .join(reward.user(), user)
                .where(reward.user().userId.eq(userId))
                .fetch();
    }
}
