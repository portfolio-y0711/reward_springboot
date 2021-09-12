package com.portfolioy0711.api._unit._2_services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolioy0711.api.services.EventService;
import com.portfolioy0711.api.services.review.ReviewEventActionRouter;
import com.portfolioy0711.api.services.review.actions.AddReviewActionHandler;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Import(EventServiceTestConfig.class)
@RunWith(SpringRunner.class)
@ContextConfiguration
public class EventServiceTest {

    private EventService eventService;
    private ReviewEventActionRouter reviewEventActionRouter;

    @Before
    public void setUp() {
        eventService = new EventService();
    }

    @Test
    public void eventTypeRouteTest() throws JsonProcessingException, ParseException {
        reviewEventActionRouter = Mockito.mock(ReviewEventActionRouter.class);
        eventService.addEventRouter("REVIEW", reviewEventActionRouter);

        Map<String, Object> body = new HashMap<String, Object>();
        body.put("type", "REVIEW");
        body.put("action", "ADD");
        body.put("content", "좋아요");
        body.put("attachedPhotoIds", new String[] {
            "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
            "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
        });
        body.put("userId", "3ede0ef2-92b7-4817-a5f3-0c575361f745");
        body.put("placeId", "2e4baf1c-5acb-4efb-a1af-eddada31b00f");

        eventService.route(body);
        verify(reviewEventActionRouter, times(1)).route(any());
    }
}
