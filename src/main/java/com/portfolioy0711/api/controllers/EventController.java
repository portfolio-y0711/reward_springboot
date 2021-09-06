package com.portfolioy0711.api.controllers;

import com.portfolioy0711.api.services.EventService;
import com.portfolioy0711.api.services.UserService;
import com.portfolioy0711.api.typings.EventDto;
import io.swagger.annotations.*;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@Api(tags = "Event")
public class EventController {
    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(value = "/events", method = POST)
    public void postEvent(@RequestBody EventDto event) {
        System.out.println(event);
    }
}
