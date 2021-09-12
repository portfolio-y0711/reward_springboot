package com.portfolioy0711.api.data;
import com.portfolioy0711.api.data.models.photo.PhotoModel;
import com.portfolioy0711.api.data.models.place.PlaceModel;
import com.portfolioy0711.api.data.models.review.ReviewModel;
import com.portfolioy0711.api.data.models.reward.RewardModel;
import com.portfolioy0711.api.data.models.user.UserModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class EventDatabase {
    private final UserModel userModel;
    private final PlaceModel placeModel;
    private final ReviewModel reviewModel;
    private final RewardModel rewardModel;
    private final PhotoModel photoModel;

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
