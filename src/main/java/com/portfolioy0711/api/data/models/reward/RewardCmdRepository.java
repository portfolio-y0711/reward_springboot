package com.portfolioy0711.api.data.models.reward;

import com.portfolioy0711.api.data.entities.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardCmdRepository extends JpaRepository<Reward, String> { }
