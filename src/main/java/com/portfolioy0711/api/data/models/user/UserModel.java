package com.portfolioy0711.api.data.models.user;

import com.portfolioy0711.api.data.entities.*;
import com.portfolioy0711.api.typings.response.QUserRewardReponse;
import com.portfolioy0711.api.typings.response.UserRewardReponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;


@Component
@RequiredArgsConstructor
public class UserModel {
    private final UserCmdRepository userCmdRepository;
    private final JPAQueryFactory query;

    public User save(User user) {
        return userCmdRepository.save(user);
    }

    public int findUserRewardPoint(String userId) {
        final QUser user = QUser.user;
        return query
                .select(user.rewardPoint)
                .from(user)
                .where(user.userId.eq(userId)).fetchOne();
    }
    public List<UserRewardReponse> findUserRewards(String userId) {
        final QUser user = QUser.user;
        final QBase base = QBase.base;
        final QReview review = QReview.review;
        final QReward reward = QReward.reward;

        return query
                .select(new QUserRewardReponse(reward.rewardId, reward.user().userId, reward.reviewId, reward.operation, reward.pointDelta, reward.reason, base.created_at))
                .innerJoin(user.reviewList,review)
                .innerJoin(user.rewardList,reward)
                .from(user)
                .fetch();
    }

    @Transactional
    public long updateRewardPoint(String userId, Integer rewardPoint) {
       final QUser user = QUser.user;
       long result = 0L;
       try {
           result = query
                   .update(user)
                   .where(user.userId.eq(userId))
                   .set(user.rewardPoint, rewardPoint)
                   .execute();
       } catch (Exception e) {
           System.out.println(e);
       }
       return result;
    }
    public List<Object> findUserCount() {
        return userCmdRepository.getUserCount();
    }

    public User findUserByUserId(String userId) {
        final QUser user = QUser.user;
        return query
                .selectFrom(user)
                .where(user.userId.eq(userId))
                .fetchOne();
    }

}
