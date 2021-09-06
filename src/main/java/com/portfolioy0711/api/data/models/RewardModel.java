package com.portfolioy0711.api.data.models;

import com.portfolioy0711.api.data.models.reward.RewardCmdRepository;
import com.portfolioy0711.api.data.models.reward.RewardQueryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RewardModel {
    RewardCmdRepository userCmdRepository;
    RewardQueryRepository userQueryRepository;
}
