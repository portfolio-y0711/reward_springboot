package com.portfolioy0711.api.services;

import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.data.models.reward.RewardModel;
import com.portfolioy0711.api.data.models.user.UserModel;
import com.portfolioy0711.api.typings.response.UserRewardReponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private EventDatabase eventDatabase;

    public UserService(EventDatabase eventDatabase) {
        this.eventDatabase = eventDatabase;
    }

    public Integer fetchUserRewardPoint (String userId) {
        UserModel userModel = eventDatabase.getUserModel();
        return userModel.findUserRewardPoint(userId);
    }

    public List<UserRewardReponse> fetchUserRewards (String userId) {
        RewardModel rewardModel = eventDatabase.getRewardModel();
        return rewardModel.findRewardsByUserId(userId);
    }
}
