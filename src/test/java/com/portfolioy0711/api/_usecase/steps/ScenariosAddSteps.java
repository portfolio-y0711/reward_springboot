package com.portfolioy0711.api._usecase.steps;

import com.portfolioy0711.api._usecase.config.TestConfig;
import com.portfolioy0711.api.data.entities.Place;
import com.portfolioy0711.api.data.entities.Review;
import com.portfolioy0711.api.data.entities.Reward;
import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Before;
import org.junit.Ignore;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@Ignore
public class ScenariosAddSteps extends TestConfig {

    @Before
    public void setUp() {
    }

    @Given("아래와 같이 특정 장소가 등록되어 있음")
    public void placeRegistered(List<Map<String, String>> places) {
//        assertEquals("!!!", places.get(0));
    }

    @And("^아래와 같이 특정 유저가 등록되어 있음$")
    public void userRegistered(List<Map<String, String>> users){
//        assertEquals("!!!", users.get(0));
    }

    @Given("^아래 장소에 대한 리뷰글이 존재하지 않음$")
    public void noReviewExists(List<Map<String, String>> placeIds){
//        assertEquals("!!!", placeIds);
    }


    @When("유저가 아래와 같이 리뷰글을 작성함")
    public void reviewCreated(List<Map<String, String>> reviews){
//        assertEquals("!!!", reviews);
    }

    @Then("유저의 리워드 레코드가 아래와 같이 생성됨")
    public void rewardCreated(List<Map<String, String>> rewards){
//        assertEquals("!!!", rewards);
    }

    @And("유저의 포인트 총점이 아래와 같아짐")
    public void order_response_equals(List<Map<String, String>> rewardPoints){
//        assertEquals("!!!", rewardPoints);
    }

    @And("유저의 리뷰 레코드가 아래와 같이 생성됨")
    public void order_response_includes(List<Map<String, String>> reviews){
//        assertEquals("!!!", reviews);
    }
}

