package com.portfolioy0711.api.data.models.reward;

import com.portfolioy0711.api.data.entities.QReward;
import com.portfolioy0711.api.data.entities.QUser;
import com.portfolioy0711.api.data.entities.Reward;
import com.portfolioy0711.api.typings.response.QUserRewardReponse;
import com.portfolioy0711.api.typings.response.UserRewardReponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RewardModel {
    private final RewardCmdRepository rewardCmdRepository;
    private final JPAQueryFactory query;

    public Reward save(Reward reward) {
        return rewardCmdRepository.save(reward);
    }

    public UserRewardReponse findLatestUserReviewRewardByReviewId(String userId, String reviewId) {
        final QUser user = QUser.user;
        final QReward reward = QReward.reward;
        return query.select(new QUserRewardReponse(reward.rewardId, reward.user().userId, reward.reviewId, reward.operation, reward.pointDelta, reward.reason, reward.created_at))
                .from(reward)
                .join(reward.user(), user)
                .where(reward.user().userId.eq(userId))
                .where(reward.reviewId.eq(reviewId))
                .fetchOne();
    }

    public List<UserRewardReponse> findRewardsByUserId(String userId) {
        final QReward reward = QReward.reward;
        final QUser user = QUser.user;

        return query.select(new QUserRewardReponse(reward.rewardId, reward.user().userId, reward.reviewId, reward.operation, reward.pointDelta, reward.reason, reward.created_at))
                .from(reward)
                .join(reward.user(), user)
                .where(reward.user().userId.eq(userId))
                .fetch();
    }

    public UserRewardReponse findLatestRewardByUserIdAndReviewId(String userId, String reviewId) {
        final QUser user = QUser.user;
        final QReward reward = QReward.reward;

        return query.select(new QUserRewardReponse(reward.rewardId, reward.user().userId, reward.reviewId, reward.operation, reward.pointDelta, reward.reason, reward.created_at))
                .from(reward)
                .join(reward.user(), user)
                .where(reward.user().userId.eq(userId))
                .where(reward.reviewId.eq(reviewId))
                .where(reward.operation.eq("ADD"))
                .orderBy(reward.created_at.desc())
                .limit(1)
                .fetchOne();

    }
}
