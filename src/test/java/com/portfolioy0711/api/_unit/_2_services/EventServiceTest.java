package com.portfolioy0711.api._unit._2_services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolioy0711.api.services.EventService;
import com.portfolioy0711.api.services.review.ReviewEventActionRouter;
import com.portfolioy0711.api.services.review.actions.AddReviewActionHandler;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import com.portfolioy0711.api.typings.vo.ReviewActionType;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Import(EventServiceTestConfig.class)
@RunWith(SpringRunner.class)
@ContextConfiguration
public class EventServiceTest {

    @Autowired
    private ApplicationContext applicationContext;
    private EventService eventService;

    @Before
    public void setUp() {
        eventService = new EventService(applicationContext);
    }

    @Test
    public void eventTypeRouteTest() throws JsonProcessingException, ParseException {
        ReviewEventDto eventInfo = ReviewEventDto
            .builder()
            .type("REVIEW")
            .action("ADD")
            .content("좋아요")
            .attachedPhotoIds(new String[] {
            "",""
            })
            .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
            .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
            .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
            .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String eventStr = objectMapper.writeValueAsString(eventInfo);

        eventService.route(eventStr);
        verify(applicationContext.getBean(ReviewEventActionRouter.class), times(1)).route(any());
    }
}
