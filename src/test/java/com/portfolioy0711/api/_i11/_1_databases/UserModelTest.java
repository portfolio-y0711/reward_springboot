package com.portfolioy0711.api._i11._1_databases;

import com.portfolioy0711.api.data.entities.QUser;
import com.portfolioy0711.api.data.entities.Review;
import com.portfolioy0711.api.data.entities.Reward;
import com.portfolioy0711.api.data.entities.User;
import com.portfolioy0711.api.data.models.RewardModel;
import com.portfolioy0711.api.data.models.UserModel;
import com.portfolioy0711.api.typings.dto.RewardDto;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserModelTest {

    @Autowired
    private JPAQueryFactory query;

    @Autowired
    UserModel userModel;

    @Autowired
    RewardModel rewardModel;

    @Test
    @Transactional
    public void saveTest() {
        QUser qUser = QUser.user;
        String userId = "13ae28fe-8b79-4808-a102-b8ffd8d06098";
        User expected = new User(userId, "James", 3);
        userModel.save(expected);
        User result = query
                .selectFrom(qUser)
                .where(qUser.userId.eq(userId))
                .fetchOne();
        assertEquals(result.getUserId(), expected.getUserId());
    }

    @Test
    @Transactional
    public void findUserRewardPointTest() {
        QUser qUser = QUser.user;
        String userId = "3ede0ef2-92b7-4817-a5f3-0c575361f745";
        User expected = new User(userId, "James", 4);
        userModel.save(expected);

        User result = query
            .selectFrom(qUser)
            .where(qUser.userId.eq(userId))
            .fetchOne();
        assertEquals(result.getRewardPoint(), expected.getRewardPoint());
    }

    @Test
    @Transactional
    public void findUserRewardsTest() {
        String userId = "3ede0ef2-92b7-4817-a5f3-0c575361f745";
        userModel.save(
            User
                .builder()
                .userId(userId)
                .name("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .rewardPoint(0)
                .build()
        );
        User user = userModel.findUserByUserId(userId);

        String rewardId = "b6ad7b39-3a76-44dc-80e0-8e5a433385b5";
        String operation = "ADD";
        Integer pointDelta = 2;
        String reason = "NEW";

        Reward reward = Reward
                .builder()
                .rewardId(rewardId)
                .user(user)
                .pointDelta(pointDelta)
                .reason(reason)
                .operation(operation)
                .build();

        rewardModel.save(reward);

        List<Reward> rewards = userModel.findUserRewards(userId);
        assertEquals(rewards.size(), 1);
    }
}
