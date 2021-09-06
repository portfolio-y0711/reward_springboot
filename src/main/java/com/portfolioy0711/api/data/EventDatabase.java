package com.portfolioy0711.api.data;
import com.portfolioy0711.api.data.models.PlaceModel;
import com.portfolioy0711.api.data.models.ReviewModel;
import com.portfolioy0711.api.data.models.RewardModel;
import com.portfolioy0711.api.data.models.UserModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class EventDatabase {
    private UserModel userModel;
    private PlaceModel placeModel;
    private ReviewModel reviewModel;
    private RewardModel rewardModel;
}
