package com.portfolioy0711.api.data;
import com.portfolioy0711.api.data.models.PlaceModel;
import com.portfolioy0711.api.data.models.ReviewModel;
import com.portfolioy0711.api.data.models.RewardModel;
import com.portfolioy0711.api.data.models.UserModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class EventDatabase {
    private UserModel userModel;
    private PlaceModel placeModel;
    private ReviewModel reviewModel;
    private RewardModel rewardModel;

    public EventDatabase(UserModel userModel, PlaceModel placeModel, ReviewModel reviewModel, RewardModel rewardModel) {
        this.userModel = userModel;
        this.placeModel = placeModel;
        this.reviewModel = reviewModel;
        this.rewardModel = rewardModel;
    }

    public UserModel getUserModel () {
        return this.userModel;
    }

    @Override
    public String toString() {
        return "EventDatabase{" +
                "userModel=" + userModel +
                ", placeModel=" + placeModel +
                ", reviewModel=" + reviewModel +
                ", rewardModel=" + rewardModel +
                '}';
    }
}
