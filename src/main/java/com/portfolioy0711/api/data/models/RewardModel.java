package com.portfolioy0711.api.data.models;

import com.portfolioy0711.api.data.entities.Reward;
import com.portfolioy0711.api.data.models.reward.RewardCmdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RewardModel {
    RewardCmdRepository rewardCmdRepository;

    public Reward save(Reward reward) {
        return rewardCmdRepository.save(reward);
    }
}
