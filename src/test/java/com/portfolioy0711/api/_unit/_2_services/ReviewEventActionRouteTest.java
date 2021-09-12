package com.portfolioy0711.api._unit._2_services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.portfolioy0711.api.services.review.ReviewEventActionRouter;
import com.portfolioy0711.api.services.review.actions.AddReviewActionHandler;
import com.portfolioy0711.api.services.review.actions.DelReviewActionHandler;
import com.portfolioy0711.api.services.review.actions.ModReviewActionHandler;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ContextConfiguration
public class ReviewEventActionRouteTest {

    private ReviewEventActionRouter reviewEventActionRouter;

    @Before
    public void setUp() {
    }

    @Test
    public void ADD_ReviewEventActionRouterTest () throws JsonProcessingException, ParseException {
        String action = "ADD";
        reviewEventActionRouter = new ReviewEventActionRouter();
        AddReviewActionHandler addReviewActionHandler = Mockito.mock(AddReviewActionHandler.class);
        reviewEventActionRouter.addRoute("ADD", addReviewActionHandler);

        ReviewEventDto eventInfo = ReviewEventDto
                .builder()
                .type("REVIEW")
                .action(action)
                .content("좋아요")
                .attachedPhotoIds(new String[] {
                    "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
                    "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
                })
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .build();

        reviewEventActionRouter.route(eventInfo);
        verify(addReviewActionHandler, times(1)).handleEvent(any());
    }

    @Test
    public void MOD_ReviewEventActionRouterTest () throws JsonProcessingException, ParseException {
        String action = "MOD";
        reviewEventActionRouter = new ReviewEventActionRouter();
        ModReviewActionHandler modReviewActionHandler = Mockito.mock(ModReviewActionHandler.class);
        reviewEventActionRouter.addRoute("MOD", modReviewActionHandler);
        ReviewEventDto eventInfo = ReviewEventDto
                .builder()
                .type("REVIEW")
                .action(action)
                .content("좋아요")
                .attachedPhotoIds(new String[] {
                        "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
                        "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
                })
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .build();

        reviewEventActionRouter.route(eventInfo);
        verify(modReviewActionHandler, times(1)).handleEvent(any());
    }

    @Test
    public void DEL_ReviewEventActionRouterTest () throws JsonProcessingException, ParseException {
        String action = "DELETE";
        reviewEventActionRouter = new ReviewEventActionRouter();
        DelReviewActionHandler delReviewActionHandler = Mockito.mock(DelReviewActionHandler.class);
        reviewEventActionRouter.addRoute("DELETE", delReviewActionHandler);
        ReviewEventDto eventInfo = ReviewEventDto
                .builder()
                .type("REVIEW")
                .action(action)
                .content("좋아요")
                .attachedPhotoIds(new String[] {
                        "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
                        "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
                })
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .build();

        reviewEventActionRouter.route(eventInfo);
        verify(delReviewActionHandler, times(1)).handleEvent(any());
    }
}
