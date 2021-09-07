package com.portfolioy0711.api.data.models;

import com.portfolioy0711.api.data.entities.QReward;
import com.portfolioy0711.api.data.entities.QUser;
import com.portfolioy0711.api.data.entities.Reward;
import com.portfolioy0711.api.data.entities.User;
import com.portfolioy0711.api.data.models.user.UserCmdRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserModel {
    @Autowired
    UserCmdRepository userCmdRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    public UserModel(UserCmdRepository userCmdRepository, JPAQueryFactory jpaQueryFactory) {
        this.userCmdRepository = userCmdRepository;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public User save(User user) {
        return userCmdRepository.save(user);
    }

    public Integer findUserRewardPoint(String userId) {
        QUser qUser = QUser.user;
        return jpaQueryFactory
                .select(qUser.rewardPoint)
                .from(qUser)
                .where(qUser.userId.eq(userId)).fetchOne();
    }
    public List<Reward> findUserRewards(String userId) {
        QReward qReward = QReward.reward;
        return jpaQueryFactory
                .select(qReward)
                .from(qReward)
                .fetch();
    }

    public long updateRewardPoint(String userId, Integer rewardPoint) {
       QUser qUser = QUser.user;
       return jpaQueryFactory
               .update(qUser)
               .where(qUser.userId.eq(userId))
               .set(qUser.rewardPoint, rewardPoint)
               .execute();
    }

    public User findUserByUserId(String userId) {
        QUser qUser = QUser.user;
        return jpaQueryFactory
                .selectFrom(qUser)
                .where(qUser.userId.eq(userId))
                .fetchOne();
    }
}
