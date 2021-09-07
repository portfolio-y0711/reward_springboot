package com.portfolioy0711.api.controllers;

import com.portfolioy0711.api.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.json.simple.JSONArray;
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
    public JSONObject getUserRewardPoint(
            @ApiParam(value = "The user id",
                    required = true, example = "3ede0ef2-92b7-4817-a5f3-0c575361f745") @PathVariable String userId) {
        userService.fetchUserRewardPoint(userId);
        JSONObject body = new JSONObject();
        JSONObject rewardPoint = new JSONObject();
        rewardPoint.put("rewardPoint", 0);
        body.put("body", rewardPoint);
        return body;
    }

    @RequestMapping(value = "/users/{userId}/rewards", method = GET)
    public JSONObject getUserRewards(
            @ApiParam(value = "The user id",
                    required = true, example = "3ede0ef2-92b7-4817-a5f3-0c575361f745") @PathVariable String userId) {
        userService.fetchUserRewards(userId);
        JSONObject body = new JSONObject();
        JSONArray arr = new JSONArray();
        JSONObject el = new JSONObject();
        el.put("rewardId", "b6ad7b39-3a76-44dc-80e0-8e5a433385b5");
        el.put("userId", "3ede0ef2-92b7-4817-a5f3-0c575361f745");
        el.put("reviewId", "240a0658-dc5f-4878-9831-ebb7b26687772");
        el.put("operation", "ADD");
        el.put("pointDelta", 2);
        el.put("reason", "NEW");
        arr.add(el);
        body.put("body", arr);
        return body;
    }
}
