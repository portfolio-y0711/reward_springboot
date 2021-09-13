package com.portfolioy0711.api._usecase.steps.DEL;

import com.portfolioy0711.api._usecase.config.TestConfig;
import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.data.entities.Place;
import com.portfolioy0711.api.data.entities.User;
import com.portfolioy0711.api.data.models.place.PlaceModel;
import com.portfolioy0711.api.data.models.review.ReviewModel;
import com.portfolioy0711.api.data.models.reward.RewardModel;
import com.portfolioy0711.api.data.models.user.UserModel;
import com.portfolioy0711.api.services.review.actions.ModReviewActionHandler;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Ignore
public class ScenariosDelSteps {

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

    @BeforeStep
    public void setUp() {
//        modReviewActionHandler = new ModReviewActionHandler(eventDatabase);
//        placeModel = eventDatabase.getPlaceModel();
//        userModel = eventDatabase.getUserModel();
//        placeModel.save(
//                Place.builder()
//                        .bonusPoint(1)
//                        .country("호주")
//                        .name("시드니")
//                        .placeId("cd1aa206-5768-4cdd-b054-661b6b35d687")
//                        .build()
//        );
//        userModel.save(
//                User.builder()
//                        .rewardPoint(3)
//                        .userId("b87d09e5-e97e-4588-badb-b5599ef95e40")
//                        .name("Jenny")
//                        .build()
//        );
    }

    @Given("아래와 같이 특정 장소가 등록되어 있음(DEL)")
    public void placeRegistered(List<Map<String, String>> places) {
//        assertEquals("!!!", places.get(0));
    }

    @And("아래와 같이 특정 유저가 등록되어 있음(DEL)")
    public void userRegistered(List<Map<String, String>> users){
//        assertEquals("!!!", users.get(0));
    }

    @Given("아래 장소에 대한 리뷰글이 존재하지 않음(DEL)")
    public void noReviewExists(List<Map<String, String>> placeIds){
//        assertEquals("!!!", placeIds);
    }


    @When("유저가 아래와 같이 리뷰글을 작성함(DEL)")
    public void reviewCreated(List<Map<String, String>> reviews){
//        assertEquals("!!!", reviews);
    }

    @Then("유저의 리워드 레코드가 아래와 같이 생성됨(DEL)")
    public void rewardCreated(List<Map<String, String>> rewards){
//        assertEquals("!!!", rewards);
    }

    @And("유저의 포인트 총점이 아래와 같아짐(DEL)")
    public void order_response_equals(List<Map<String, String>> rewardPoints){
//        assertEquals("!!!", rewardPoints);
    }

    @And("유저의 리뷰 레코드가 아래와 같이 생성됨(DEL)")
    public void order_response_includes(List<Map<String, String>> reviews){
//        assertEquals("!!!", reviews);
    }
}

