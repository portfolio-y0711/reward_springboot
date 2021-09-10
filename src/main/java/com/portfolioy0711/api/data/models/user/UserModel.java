package com.portfolioy0711.api.data.models.user;

import com.portfolioy0711.api.data.entities.*;
import com.portfolioy0711.api.typings.response.QUserRewardReponse;
import com.portfolioy0711.api.typings.response.UserRewardReponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserModel {
    @Autowired
    UserCmdRepository userCmdRepository;

    @Autowired
    private JPAQueryFactory query;

    public UserModel(UserCmdRepository userCmdRepository, JPAQueryFactory query) {
        this.userCmdRepository = userCmdRepository;
        this.query = query;
    }

    public User save(User user) {
        return userCmdRepository.save(user);
    }

    public Integer findUserRewardPoint(String userId) {
        QUser user = QUser.user;
        return query
                .select(user.rewardPoint)
                .from(user)
                .where(user.userId.eq(userId)).fetchOne();
    }
    public List<UserRewardReponse> findUserRewards(String userId) {
        QUser user = QUser.user;
        QReview review = QReview.review;
        QReward reward = QReward.reward;

        return query
                .select(new QUserRewardReponse(reward.rewardId, reward.user().userId, reward.reviewId, reward.operation, reward.pointDelta, reward.reason))
                .innerJoin(user.reviewList,review)
                .innerJoin(user.rewardList,reward)
                .from(user)
                .fetch();
    }

    public long updateRewardPoint(String userId, Integer rewardPoint) {
       QUser user = QUser.user;
       return query
               .update(user)
               .where(user.userId.eq(userId))
               .set(user.rewardPoint, rewardPoint)
               .execute();
    }

    public User findUserByUserId(String userId) {
        QUser user = QUser.user;
        return query
                .selectFrom(user)
                .where(user.userId.eq(userId))
                .fetchOne();
    }

}
