package com.portfolioy0711.api.services;

import com.portfolioy0711.api.data.models.user.UserCmdRepository;
import com.portfolioy0711.api.typings.response.UserRewardPointDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserCmdRepository userRepository;

    public UserService(UserCmdRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRewardPointDto fetchUserRewardPoint (String UserId) {
        return new UserRewardPointDto(0);
    }
}
