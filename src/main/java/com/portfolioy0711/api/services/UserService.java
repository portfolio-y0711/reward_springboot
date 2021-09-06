package com.portfolioy0711.api.services;

import com.portfolioy0711.api.typings.UserRewardPointDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public UserRewardPointDto fetchUserRewardPoint (String UserId) {
        UserRewardPointDto response = new UserRewardPointDto(0);
        return response;
    }
}
