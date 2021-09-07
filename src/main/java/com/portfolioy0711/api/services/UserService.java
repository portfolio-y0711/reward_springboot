package com.portfolioy0711.api.services;

import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.data.entities.Reward;
import com.portfolioy0711.api.data.models.UserModel;
import com.portfolioy0711.api.data.models.user.UserCmdRepository;
import com.portfolioy0711.api.typings.response.UserRewardPointDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private EventDatabase eventDatabase;

    public UserService(EventDatabase eventDatabase) {
        this.eventDatabase = eventDatabase;
    }

    public UserRewardPointDto fetchUserRewardPoint (String userId) {
        UserModel userModel = eventDatabase.getUserModel();
        userModel.findUserRewardPoint(userId);
        return new UserRewardPointDto(0);
    }

    public List<Reward> fetchUserRewards (String userId) {
        UserModel userModel = eventDatabase.getUserModel();
        userModel.findUserRewards(userId);
        return new ArrayList<Reward>();
    }
}
