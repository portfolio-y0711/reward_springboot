package com.portfolioy0711.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.portfolioy0711.api.controllers.event.EventMapper;
import com.portfolioy0711.api.typings.EventRouter;

import com.portfolioy0711.api.typings.vo.event.EventTypeEnum;
import lombok.NoArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EventService {

    public EventService() { }

    @Autowired
    private ApplicationContext context;

    Map<String, EventRouter> routes = new HashMap<>();

    public EventService addEventRouter(String path, EventRouter router) {
        routes.put(path, router);
        return this;
    }

    public void route(Object body) throws ParseException, JsonProcessingException {
        EventMapper eventMapper = new EventMapper(body);

        String eventType = eventMapper
            .validate("type", EventTypeEnum.getEventTypes())
            .getValueAsType("type");

        this.routes.get(eventType).route(body);
    }
}

