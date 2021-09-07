package com.portfolioy0711.api._unit._2_services;

import com.portfolioy0711.api.services.review.ReviewEventActionRouter;
import com.portfolioy0711.api.services.review.actions.AddReviewActionHandler;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ContextConfiguration
public class ReviewEventActionRouteTest {

    @Autowired
    private ApplicationContext applicationContext;
    private ReviewEventActionRouter reviewEventActionRouter;

    @Before
    public void setUp() {
    }

    @Test
    public void ADD_ReviewEventActionRouterTest () {
        String action = "ADD";
        reviewEventActionRouter = new ReviewEventActionRouter(applicationContext);
        reviewEventActionRouter.removeRoute("ADD");
        AddReviewActionHandler addReviewActionHandler = Mockito.mock(AddReviewActionHandler.class);
        reviewEventActionRouter.addRoute("ADD", addReviewActionHandler);
        ReviewEventDto eventInfo = ReviewEventDto
                .builder()
                .type("REVIEW")
                .action(action)
                .content("좋아요")
                .attachedPhotoIds(new String[] {
                        "",""
                })
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .build();

        reviewEventActionRouter.route(eventInfo);
        verify(addReviewActionHandler, times(1)).handleEvent(any());
    }

    @Test
    public void MOD_ReviewEventActionRouterTest () {
        String action = "MOD";
        reviewEventActionRouter = new ReviewEventActionRouter(applicationContext);
        reviewEventActionRouter.removeRoute("MOD");
        AddReviewActionHandler addReviewActionHandler = Mockito.mock(AddReviewActionHandler.class);
        reviewEventActionRouter.addRoute("MOD", addReviewActionHandler);
        ReviewEventDto eventInfo = ReviewEventDto
                .builder()
                .type("REVIEW")
                .action(action)
                .content("좋아요")
                .attachedPhotoIds(new String[] {
                        "",""
                })
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .build();

        reviewEventActionRouter.route(eventInfo);
        verify(addReviewActionHandler, times(1)).handleEvent(any());
    }

    @Test
    public void DEL_ReviewEventActionRouterTest () {
        String action = "DEL";
        reviewEventActionRouter = new ReviewEventActionRouter(applicationContext);
        reviewEventActionRouter.removeRoute("DEL");
        AddReviewActionHandler addReviewActionHandler = Mockito.mock(AddReviewActionHandler.class);
        reviewEventActionRouter.addRoute("DEL", addReviewActionHandler);
        ReviewEventDto eventInfo = ReviewEventDto
                .builder()
                .type("REVIEW")
                .action(action)
                .content("좋아요")
                .attachedPhotoIds(new String[] {
                        "",""
                })
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .build();

        reviewEventActionRouter.route(eventInfo);
        verify(addReviewActionHandler, times(1)).handleEvent(any());
    }
}
