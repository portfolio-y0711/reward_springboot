package com.portfolioy0711.api.services;

import com.portfolioy0711.api.data.UserRepository;
import com.portfolioy0711.api.typings.UserRewardPointDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRewardPointDto fetchUserRewardPoint (String UserId) {
        return new UserRewardPointDto(0);
    }
}
