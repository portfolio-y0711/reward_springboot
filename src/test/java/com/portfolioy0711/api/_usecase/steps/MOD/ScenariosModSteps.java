package com.portfolioy0711.api._usecase.steps.MOD;

import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.data.entities.Place;
import com.portfolioy0711.api.data.entities.Review;
import com.portfolioy0711.api.data.entities.Reward;
import com.portfolioy0711.api.data.entities.User;
import com.portfolioy0711.api.data.models.place.PlaceModel;
import com.portfolioy0711.api.data.models.review.ReviewModel;
import com.portfolioy0711.api.data.models.reward.RewardModel;
import com.portfolioy0711.api.data.models.user.UserModel;
import com.portfolioy0711.api.services.review.actions.ModReviewActionHandler;
import com.portfolioy0711.api.typings.response.ReviewResponse;
import com.portfolioy0711.api.typings.response.UserRewardReponse;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.junit.Ignore;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Ignore
public class ScenariosModSteps {
    @Autowired
    private PlaceModel placeModel;

    @Autowired
    private UserModel userModel;

    @Autowired
    private ReviewModel reviewModel;

    @Autowired
    private RewardModel rewardModel;

    @Autowired
    private EventDatabase eventDatabase;

    private ModReviewActionHandler modReviewActionHandler;
    private final Logger logger = LoggerFactory.getLogger(ScenariosModSteps.class);

    @Before
    @Transactional
    public void setUp() {
        modReviewActionHandler = new ModReviewActionHandler(eventDatabase);
        placeModel = eventDatabase.getPlaceModel();
        userModel = eventDatabase.getUserModel();
        Place place = placeModel.save(
            Place.builder()
                .bonusPoint(1)
                .country("호주")
                .name("시드니")
                .placeId("cd1aa206-5768-4cdd-b054-661b6b35d687")
                .build()
        );
        User user = userModel.save(
            User.builder()
                .rewardPoint(1)
                .userId("b87d09e5-e97e-4588-badb-b5599ef95e40")
                .name("Jenny")
                .build()
        );
        reviewModel.save(
            Review.builder()
            .reviewId("ebb3e526-fd9e-448c-b4f7-665b814150a6")
            .content("시드니도 좋아요")
            .place(place)
            .photos(new HashSet<>())
            .user(user)
            .rewarded(1)
            .build()
        );
        rewardModel.save(
            Reward.builder()
                .reviewId("ebb3e526-fd9e-448c-b4f7-665b814150a6")
                .rewardId("81c20067-e377-41a8-ae77-3f1cd4689beb")
                .user(user)
                .pointDelta(1)
                .operation("ADD")
                .reason("NEW")
            .build()
        );
    }

    @Given("아래와 같이 특정 장소가 등록되어 있음_2")
    public void placeRegistered(List<Place> places) {
        assertEquals(places.get(0), placeModel.findPlaceByPlaceId(places.get(0).getPlaceId()));
    }

    @And("아래와 같이 특정 유저가 등록되어 있음_2")
    public void userRegistered(List<User> users){
        logger.error(users.get(0).toString());
        logger.error(userModel.findUserByUserId(users.get(0).getUserId()).toString());
        assertEquals(users.get(0), userModel.findUserByUserId(users.get(0).getUserId()));
    }

    @And("유저가 아래와 같이 특정 장소에 대해 리뷰를 작성하였음_2")
    public void reviewForPlaceExists(List<Map<String, String>> reviews) {
        Map<String, String> review = reviews.get(0);
        String type = review.get("type");
        String action = review.get("action");
        String reviewId = review.get("reviewId");
        int rewarded = Integer.parseInt(review.get("rewarded"));
        String content = review.get("content");
        String userId = review.get("userId");
        String placeId = review.get("placeId");

        logger.error(UUID.randomUUID().toString());
        ReviewResponse expected = new ReviewResponse(reviewId, placeId, userId, content, rewarded, new HashSet<>());

        List<Object[]> queryResults = reviewModel.findReviewInfoByReviewId_native("b87d09e5-e97e-4588-badb-b5599ef95e40");
        Set<String> photoIds = queryResults.stream().filter(list -> ((String) list[5]) != null).map(list -> ((String) list[5]) ).collect(Collectors.toSet());
        logger.error(photoIds.toString());

        Object[] queryResult = queryResults.get(0);
        ReviewResponse actual = new ReviewResponse(
                ((String) queryResult[0]),
                ((String) queryResult[1]),
                ((String) queryResult[2]),
                ((String) queryResult[3]),
                ((int) queryResult[4]),
                new HashSet<>(photoIds)
        );

        assertEquals(expected, actual);
    }

    @And("리뷰 작성에 대한 보상으로 아래와 같이 유저에게 포인트가 부여되었음_2")
    public void rewardHistoryExists(List<UserRewardReponse> rewards){
        UserRewardReponse expected = rewards.get(0);
        UserRewardReponse actual = rewardModel.findLatestRewardByUserIdAndReviewId(rewards.get(0).getUserId(), rewards.get(0).getReviewId());
        String[] excludeFields = new String[]{ "rewardId", "created_at" };
        assertTrue(new ReflectionEquals(expected, excludeFields).matches(actual));
    }

//    @Given("유저의 현재 표인트 총점은 아래와 같음_2")
//    public void currentPoint(List<Map<String, String>> reviews){
//        assertEquals("!!!", reviews);
//    }
//
//    @When("유저가 아래와 같이 작성했던 리뷰를 수정함_2")
//    public void reviewCreated(List<Map<String, String>> reviews){
//        assertEquals("!!!", reviews);
//    }
//
//    @Then("유저의 리워드 레코드가 아래와 같이 변경됨_2")
//    public void rewardCreated(List<Map<String, String>> rewards){
////        assertEquals("!!!", rewards);
//    }
//
//    @And("유저의 포인트 총점이 아래와 같아짐_2")
//    public void order_response_equals(List<Map<String, String>> rewardPoints){
////        assertEquals("!!!", rewardPoints);
//    }
//
//    @And("유저의 리뷰 레코드가 아래와 같이 변경됨_2")
//    public void order_response_includes(List<Map<String, String>> reviews){
////        assertEquals("!!!", reviews);
//    }
}

