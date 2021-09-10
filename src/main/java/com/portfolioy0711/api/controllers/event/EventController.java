package com.portfolioy0711.api.controllers.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolioy0711.api.services.EventService;
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

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(value = "/events", method = POST)
    public void postEvent(@RequestBody Object body) throws ParseException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String eventStr = mapper.writeValueAsString(body);
        eventService.route(eventStr);
    }
}
