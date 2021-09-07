package com.portfolioy0711.api.data.models;

import com.portfolioy0711.api.data.entities.Reward;
import com.portfolioy0711.api.data.models.reward.RewardCmdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RewardModel {

    @Autowired
    RewardCmdRepository rewardCmdRepository;

    public Reward save(Reward reward) {
        return rewardCmdRepository.save(reward);
    }
}
