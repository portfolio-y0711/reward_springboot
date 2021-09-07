package com.portfolioy0711.api._usecase;

import com.portfolioy0711.api._usecase.config.TestConfig;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        extraGlue = {"com.portfolioy0711.api._usercase.steps"},
        features = {"src/test/resources/features"})

public class CucumberTest extends TestConfig { }

