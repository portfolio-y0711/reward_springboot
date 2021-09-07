package com.portfolioy0711.api._unit._2_services;

import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.data.models.PlaceModel;
import com.portfolioy0711.api.data.models.ReviewModel;
import com.portfolioy0711.api.data.models.RewardModel;
import com.portfolioy0711.api.data.models.UserModel;
import com.portfolioy0711.api.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    private EventDatabase eventDatabase;
    private UserModel userModel;
    private PlaceModel placeModel;
    private ReviewModel reviewModel;
    private RewardModel rewardModel;
    private UserService userService;

    @Before
    public void setUp() {
        userModel = Mockito.mock(UserModel.class);
        placeModel = Mockito.mock(PlaceModel.class);
        reviewModel = Mockito.mock(ReviewModel.class);
        rewardModel = Mockito.mock(RewardModel.class);
        eventDatabase = new EventDatabase(userModel, placeModel, reviewModel, rewardModel);
        userService = new UserService(eventDatabase);
    }

    @Test
    public void fetchRewardPointTest() {
       userService.fetchUserRewardPoint("3ede0ef2-92b7-4817-a5f3-0c575361f745");
       verify(userModel, times(1)).findUserRewardPoint(anyString());
    }

    @Test
    public void fetchRewardsTest() {
        userService.fetchUserRewards("3ede0ef2-92b7-4817-a5f3-0c575361f745");
        verify(userModel, times(1)).findUserRewards(anyString());
    }
}
