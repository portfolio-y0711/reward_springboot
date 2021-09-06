package com.portfolioy0711.api.controllers;

import com.portfolioy0711.api.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@Api(tags = "User")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users/{userId}/rewardPoint", method = GET)
    public JSONObject getUsers(
            @ApiParam(value = "The user id",
                    required = true, example = "3ede0ef2-92b7-4817-a5f3-0c575361f745") @PathVariable String userId) {
        userService.fetchUserRewardPoint(userId);
        JSONObject body = new JSONObject();
        JSONObject rewardPoint = new JSONObject();
        rewardPoint.put("rewardPoint", 0);
        body.put("body", rewardPoint);
        return body;
    }
}
