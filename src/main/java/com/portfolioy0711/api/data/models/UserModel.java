package com.portfolioy0711.api.data.models;

import com.portfolioy0711.api.data.entities.*;
import com.portfolioy0711.api.data.models.user.UserCmdRepository;
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
        QUser qUser = QUser.user;
        return query
                .select(qUser.rewardPoint)
                .from(qUser)
                .where(qUser.userId.eq(userId)).fetchOne();
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
       QUser qUser = QUser.user;
       return query
               .update(qUser)
               .where(qUser.userId.eq(userId))
               .set(qUser.rewardPoint, rewardPoint)
               .execute();
    }

    public User findUserByUserId(String userId) {
        QUser qUser = QUser.user;
        return query
                .selectFrom(qUser)
                .where(qUser.userId.eq(userId))
                .fetchOne();
    }

}
