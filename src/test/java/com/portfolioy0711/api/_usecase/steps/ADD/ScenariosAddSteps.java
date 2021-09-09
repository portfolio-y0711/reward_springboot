package com.portfolioy0711.api._usecase.steps.ADD;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.data.entities.Place;
import static org.junit.Assert.assertEquals;

import com.portfolioy0711.api.data.entities.Review;
import com.portfolioy0711.api.data.entities.Reward;
import com.portfolioy0711.api.data.entities.User;
import com.portfolioy0711.api.data.models.PlaceModel;
import com.portfolioy0711.api.data.models.ReviewModel;
import com.portfolioy0711.api.data.models.RewardModel;
import com.portfolioy0711.api.data.models.UserModel;
import com.portfolioy0711.api.services.review.actions.AddReviewActionHandler;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Ignore
public class ScenariosAddSteps {
    @Autowired
    private PlaceModel placeModel;

    @Autowired
    private UserModel userModel;

    @Autowired
    private ReviewModel reviewModel;

    @Autowired
    private RewardModel rewardModel;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EventDatabase eventDatabase;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AddReviewActionHandler addReviewActionHandler;

    @Before
    public void setUp() {
        placeModel = eventDatabase.getPlaceModel();
        userModel = eventDatabase.getUserModel();
        placeModel.save(
                Place.builder()
                        .bonusPoint(1)
                        .country("호주")
                        .name("멜번")
                        .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                        .build()
        );
        userModel.save(
                User.builder()
                        .rewardPoint(0)
                        .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                        .name("Michael")
                        .build()
        );
    }

    @Transactional
    @Given("아래와 같이 특정 장소가 등록되어 있음_1")
    public void placeRegistered(List<Place> places) {
        assertEquals(places.get(0), placeModel.findPlaceByPlaceId(places.get(0).getPlaceId()));
    }

    @Transactional
    @And("아래와 같이 특정 유저가 등록되어 있음_1")
    public void userRegistered(List<User> users){
        assertEquals(users.get(0), userModel.findUserByUserId(users.get(0).getUserId()));
    }

    @Transactional
    @Given("아래 장소에 대한 리뷰글이 존재하지 않음_1")
    public void noReviewExists(List<Map<String, String>> placeIds){
        Integer reviewCount = reviewModel.findReviewCountsByPlaceId(placeIds.get(0).get("placeId"));
        assertEquals(java.util.Optional.ofNullable(reviewCount), Optional.of(0));
    }


    @Transactional
    @When("유저가 아래와 같이 리뷰글을 작성함_1")
    public void reviewCreated(List<Map<String,String>> reviews){
        Map<String, String> review = reviews.get(0);
        String type = review.get("type");
        String action = review.get("action");
        String reviewId = review.get("reviewId");
        String content = review.get("content");
        String[] attachedPhotoIds = review.get("attachedPhotoIds").split(",");
        String userId = review.get("userId");
        String placeId = review.get("placeId");
        ReviewEventDto eventInfo = ReviewEventDto.builder()
            .type(type)
            .userId(userId)
            .reviewId(reviewId)
            .placeId(placeId)
            .attachedPhotoIds(attachedPhotoIds)
            .content(content)
            .action(action)
            .build();

        addReviewActionHandler.handleEvent(eventInfo);
    }

    @Transactional
    @Then("유저의 리워드 레코드가 아래와 같이 생성됨_1")
    public void rewardCreated(List<Map<String,String>> rewards) throws JsonProcessingException {
        Map<String, String> reward = rewards.get(0);
        String userId = reward.get("userId");
        String reviewId = reward.get("reviewId");
        String operation = reward.get("operation");
        String pointDelta = reward.get("pointDelta");
        String reason = reward.get("reason");

        Reward rewardRecord =  rewardModel.findLatestUserReviewRewardByReviewId(userId, reviewId);

        assertEquals(rewardRecord, "");
//        Reward rewardRecord =  rewardModel.findRewards().get(0);
//        ObjectMapper mapper = new ObjectMapper();
//        String rewardStr = mapper.writeValueAsString(rewardRecord);
//
//        assertEquals(rewardStr, mapper.writeValueAsString(reward));
    }

    @Transactional
    @And("유저의 포인트 총점이 아래와 같아짐_1")
    public void order_response_equals(List<Map<String, String>> rewardPoints){
//        assertEquals("!!!", rewardPoints);
    }

    @Transactional
    @And("유저의 리뷰 레코드가 아래와 같이 생성됨_1")
    public void order_response_includes(List<Map<String, String>> reviews){
//        assertEquals("!!!", reviews);
    }
}

