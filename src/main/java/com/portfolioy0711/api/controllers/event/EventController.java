package com.portfolioy0711.api.controllers.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.services.EventService;
import com.portfolioy0711.api.services.blarblar.BlarBlarEventActionRouter;
import com.portfolioy0711.api.services.blarblar.actions.A_ActionHandler;
import com.portfolioy0711.api.services.blarblar.actions.B_ActionHandler;
import com.portfolioy0711.api.services.blarblar.actions.C_ActionHandler;
import com.portfolioy0711.api.services.review.ReviewEventActionRouter;
import com.portfolioy0711.api.services.review.actions.AddReviewActionHandler;
import com.portfolioy0711.api.services.review.actions.DelReviewActionHandler;
import com.portfolioy0711.api.services.review.actions.ModReviewActionHandler;
import com.portfolioy0711.api.typings.EventRouter;
import com.portfolioy0711.api.typings.vo.event.EventTypeEnum;
import com.portfolioy0711.api.util.EventValidator;
import io.swagger.annotations.*;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@Api(tags = "Event")
public class EventController {
    private EventService eventService;

    public EventController(EventService eventService, EventDatabase eventDatabase) {
        this.eventService = eventService;

        EventRouter reviewEventRouter = new ReviewEventActionRouter()
            .addRoute("ADD", new AddReviewActionHandler(eventDatabase))
            .addRoute("MOD", new ModReviewActionHandler(eventDatabase))
            .addRoute("DELETE", new DelReviewActionHandler(eventDatabase));

        EventRouter blarblarEventRouter = new BlarBlarEventActionRouter()
            .addRoute("A", new A_ActionHandler(eventDatabase))
            .addRoute("B", new B_ActionHandler(eventDatabase))
            .addRoute("C", new C_ActionHandler(eventDatabase));

        this.eventService
                .addEventRouter("REVIEW", reviewEventRouter)
                .addEventRouter("BLAR_BLAR", blarblarEventRouter);

    }
    @ApiImplicitParams(
        @ApiImplicitParam(name = "body", dataTypeClass = String.class, paramType = "body", example = "{ \"type\": \"REVIEW\", \"action\": \"ADD\", \"reviewId\": \"240a0658-dc5f-4878-9831-ebb7b26687772\", \"content\": \"좋아요\", \"attachedPhotoIds\": [ \"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8\", \"afb0cef2-851d-4a50-bb07-9cc15cbdc332\" ], \"userId\": \"3ede0ef2-92b7-4817-a5f3-0c575361f745\", \"placeId\": \"2e4baf1c-5acb-4efb-a1af-eddada31b00f\" }")
    )
    @RequestMapping(value = "/events", method = POST)
    public void postEvent(@RequestBody Object body) throws ParseException, JsonProcessingException {
        EventValidator eventValidator = new EventValidator(body);
        eventValidator.validate("type", EventTypeEnum.getEventTypes());
        this.eventService.route(body);
    }
}
