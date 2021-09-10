package com.portfolioy0711.api.data;
import com.portfolioy0711.api.data.models.photo.PhotoModel;
import com.portfolioy0711.api.data.models.place.PlaceModel;
import com.portfolioy0711.api.data.models.review.ReviewModel;
import com.portfolioy0711.api.data.models.reward.RewardModel;
import com.portfolioy0711.api.data.models.user.UserModel;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class EventDatabase {
    private UserModel userModel;
    private PlaceModel placeModel;
    private ReviewModel reviewModel;
    private RewardModel rewardModel;
    private PhotoModel photoModel;

    public EventDatabase(UserModel userModel, PlaceModel placeModel, ReviewModel reviewModel, RewardModel rewardModel, PhotoModel photoModel) {
        this.userModel = userModel;
        this.placeModel = placeModel;
        this.reviewModel = reviewModel;
        this.rewardModel = rewardModel;
        this.photoModel = photoModel;
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
