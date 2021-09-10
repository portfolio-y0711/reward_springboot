package com.portfolioy0711.api._usecase;

import com.portfolioy0711.api.ApiApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@CucumberOptions(
        plugin = {"pretty"},
        extraGlue = {"com.portfolioy0711.api._usercase.steps"},
        features = {"src/test/resources/features"})
@SpringBootTest(classes = {ApiApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberTest{}

