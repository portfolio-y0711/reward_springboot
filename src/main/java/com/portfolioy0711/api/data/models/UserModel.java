package com.portfolioy0711.api.data.models;

import com.portfolioy0711.api.data.entities.QUser;
import com.portfolioy0711.api.data.models.user.UserCmdRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public Integer findUserRewardPoint(String userId) {
        QUser qUser = QUser.user;
        return jpaQueryFactory
                .select(qUser.rewardPoint)
                .from(qUser)
                .where(qUser.userId.eq(userId)).fetchOne();
    }
    public long updateRewardPoint(String userId, Integer rewardPoint) {
       QUser qUser = QUser.user;
       return jpaQueryFactory
               .update(qUser)
               .where(qUser.userId.eq(userId))
               .set(qUser.rewardPoint, rewardPoint)
               .execute();
    }

}
