package com.portfolioy0711.api._usecase;

import com.portfolioy0711.api.ApiApplication;
import com.portfolioy0711.api._usecase.config.TestConfig;
import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.data.entities.Place;
import com.portfolioy0711.api.data.models.PlaceModel;
import com.portfolioy0711.api.services.review.actions.AddReviewActionHandler;
import io.cucumber.java.Before;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@CucumberOptions(
        plugin = {"pretty"},
        extraGlue = {"com.portfolioy0711.api._usercase.steps"},
        features = {"src/test/resources/features"})
@SpringBootTest(classes = {ApiApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberTest{}

//    @Autowired
//    protected AddReviewActionHandler addReviewActionHandler;
//
//    @Autowired
//    protected EventDatabase eventDatabase;
//    protected PlaceModel placeModel;
//
//    @Before
//    public void setUp() {
//        placeModel = eventDatabase.getPlaceModel();
//        Place result = placeModel.save(
//                Place.builder()
//                        .bonusPoint(1)
//                        .country("호주")
//                        .name("멜번")
//                        .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
//                        .build()
//        );
//        System.out.println(result);
//    }

