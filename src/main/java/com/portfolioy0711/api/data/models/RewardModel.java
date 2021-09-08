package com.portfolioy0711.api.data.models;

import com.portfolioy0711.api.data.entities.QReward;
import com.portfolioy0711.api.data.entities.QUser;
import com.portfolioy0711.api.data.entities.Reward;
import com.portfolioy0711.api.data.models.reward.RewardCmdRepository;
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

    public Reward findLatestUserReviewRewardByReviewId(String userId, String reviewId) {
        QUser qUser = QUser.user;
        QReward qReward = QReward.reward;
        return query.select(qReward)
                .from(qReward)
                .where(qReward.rewardId.eq(reviewId))
                .leftJoin(qUser).on(qReward.user().eq(qUser))
                .fetchOne();
    }

    public List<Reward> findRewards() {
        QReward qReward = QReward.reward;
        return query.select(qReward)
                .from(qReward)
                .fetch();
    }
}
