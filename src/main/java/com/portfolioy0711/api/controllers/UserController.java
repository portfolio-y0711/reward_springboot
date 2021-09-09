package com.portfolioy0711.api.controllers;

import com.portfolioy0711.api.services.UserService;
import com.portfolioy0711.api.typings.response.UserRewardReponse;
import com.portfolioy0711.api.typings.response.UserRewardPointResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@Api(tags = "User")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users/{userId}/rewardPoint", method = GET)
    public UserRewardPointResponse getUserRewardPoint(
            @ApiParam(value = "The user id",
                    required = true, example = "3ede0ef2-92b7-4817-a5f3-0c575361f745") @PathVariable String userId) {
        Integer userRewardPoint = userService.fetchUserRewardPoint(userId);
        return new UserRewardPointResponse(userRewardPoint);
    }

    @RequestMapping(value = "/users/{userId}/rewards", method = GET)
    public List<UserRewardReponse> getUserRewards(
            @ApiParam(value = "The user id",
                    required = true, example = "3ede0ef2-92b7-4817-a5f3-0c575361f745") @PathVariable String userId) {
        return userService.fetchUserRewards(userId);
    }
}
