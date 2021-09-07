package com.portfolioy0711.api._unit._1_controllers;

import com.portfolioy0711.api.controllers.UserController;
import com.portfolioy0711.api.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;


public class UserControllerTest {
    private UserController userController;
    private UserService userService;

    @Before
    public void setUp() {
        userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void getUserRewardPointTest() {
        userController.getUserRewardPoint("3ede0ef2-92b7-4817-a5f3-0c575361f745");
        verify(userService, times(1)).fetchUserRewardPoint(anyString());
    }

    @Test
    public void getUserRewardsTest() {
        userController.getUserRewardPoint("3ede0ef2-92b7-4817-a5f3-0c575361f745");
        verify(userService, times(1)).fetchUserRewardPoint(anyString());
    }
}
