package com.portfolioy0711.api._unit._2_services;

import com.portfolioy0711.api.services.review.ReviewEventActionRouter;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class EventServiceTestConfig {

    @Bean
    public ReviewEventActionRouter reviewEventHandler() {
       ReviewEventActionRouter reviewEventHandler = Mockito.mock(ReviewEventActionRouter.class);
       return reviewEventHandler;
    }
}
