package com.portfolioy0711.api._usecase.steps.ADD;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.data.entities.Place;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.portfolioy0711.api.data.entities.User;
import com.portfolioy0711.api.data.models.place.PlaceModel;
import com.portfolioy0711.api.data.models.review.ReviewModel;
import com.portfolioy0711.api.data.models.reward.RewardModel;
import com.portfolioy0711.api.data.models.user.UserModel;
import com.portfolioy0711.api.services.review.actions.AddReviewActionHandler;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import com.portfolioy0711.api.typings.response.UserRewardReponse;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Ignore;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
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
    public void rewardCreated(List<UserRewardReponse> rewards) throws JsonProcessingException {
        UserRewardReponse expected = rewards.get(0);
        UserRewardReponse actual =  rewardModel.findLatestUserReviewRewardByReviewId(expected.getUserId(), expected.getReviewId());
        String[] excludeFields = new String[]{ "rewardId" };
        assertTrue(new ReflectionEquals(expected, excludeFields).matches(actual));
    }

    @Transactional
    @And("유저의 포인트 총점이 아래와 같아짐_1")
    public void order_response_equals(List<Map<String, String>> rewardPointInfo){
        String userId = rewardPointInfo.get(0).get("userId");
        int expected = Integer.parseInt(rewardPointInfo.get(0).get("rewardPoint"));
        int actual = userModel.findUserRewardPoint(userId);
        assertEquals(expected, actual);
    }

    @Transactional
    @And("유저의 리뷰 레코드가 아래와 같이 생성됨_1")
    public void order_response_includes(List<Map<String, String>> reviews){
        Map<String, String> review = reviews.get(0);
        String reviewId = review.get("reviewId");
        String placeId = review.get("placeId");
        String contentId = review.get("content");
        String[] attachedPhotoIds = review.get("attachedPhotoIds").split(",");
        String userId = review.get("userId");
        int rewarded = Integer.parseInt(review.get("rewarded"));

//        Review review = reviewModel.findReviewByReviewId(reviewId);


        assertEquals(rewarded, "");
    }
}

