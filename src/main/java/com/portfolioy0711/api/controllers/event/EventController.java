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

    @RequestMapping(value = "/events", method = POST)
    public void postEvent(@RequestBody Object body) throws ParseException, JsonProcessingException {
        EventMapper eventMapper = new EventMapper(body);
        eventMapper.validate("type", EventTypeEnum.getEventTypes());
        this.eventService.route(body);
    }
}
