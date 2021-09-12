package com.portfolioy0711.api._i11._1_databases;

import com.portfolioy0711.api.data.entities.Reward;
import com.portfolioy0711.api.data.entities.User;
import com.portfolioy0711.api.data.models.reward.RewardModel;
import com.portfolioy0711.api.data.models.user.UserModel;
import com.portfolioy0711.api.typings.response.UserRewardReponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RewardModelTest {

    @Autowired
    private JPAQueryFactory query;

    @Autowired
    UserModel userModel;

    @Autowired
    RewardModel rewardModel;

    @Test
    @Transactional
    public void findRewardsByUserIdTest() {
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
        String reviewId = "240a0658-dc5f-4878-9831-ebb7b26687772";
        String operation = "ADD";
        Integer pointDelta = 2;
        String reason = "NEW";

        Reward reward = Reward
                .builder()
                .rewardId(rewardId)
                .reviewId(reviewId)
                .user(user)
                .pointDelta(pointDelta)
                .reason(reason)
                .operation(operation)
                .build();

        rewardModel.save(reward);
        List<UserRewardReponse> result = rewardModel.findRewardsByUserId(user.getUserId());
        System.out.println(result);

        assertEquals(result.size(), 1);
    }

    @Test
    @Transactional
    public void findLatestRewardByUserIdAndReviewId() {
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

        String add_operation = "ADD";
        String add_reason = "NEW";

        String reviewId = "240a0658-dc5f-4878-9831-ebb7b26687772";
        Integer pointDelta = 2;

        String sub_operation = "SUB";
        String sub_reason = "DEL";

        String rewardId = "b6ad7b39-3a76-44dc-80e0-8e5a433385b5";

        Reward add_reward = Reward
                .builder()
                .rewardId("sldkfjlsdfk")
                .reviewId(reviewId)
                .user(user)
                .pointDelta(pointDelta)
                .reason(add_reason)
                .operation(add_operation)
                .build();

        Reward sub_reward = Reward
                .builder()
                .rewardId(rewardId)
                .reviewId(reviewId)
                .user(user)
                .pointDelta(pointDelta)
                .reason(sub_reason)
                .operation(sub_operation)
                .build();

        rewardModel.save(add_reward);
        rewardModel.save(sub_reward);

        List<UserRewardReponse> result = rewardModel.findRewardsByUserId(user.getUserId());
        System.out.println(result);

        assertEquals(result.size(), 1);
    }
}
