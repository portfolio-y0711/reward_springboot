package com.portfolioy0711.api.data.models;

import com.portfolioy0711.api.data.models.user.UserCmdRepository;
import com.portfolioy0711.api.data.models.user.UserQueryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserModel {
    UserCmdRepository userCmdRepository;
    UserQueryRepository userQueryRepository;

    public UserModel(
        UserCmdRepository userCmdRepository,
        UserQueryRepository userQueryRepository
    ) {
        this.userCmdRepository = userCmdRepository;
        this.userQueryRepository = userQueryRepository;
    }
}
