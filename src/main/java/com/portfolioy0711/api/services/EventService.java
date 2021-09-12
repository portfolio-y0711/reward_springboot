package com.portfolioy0711.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.portfolioy0711.api.util.EventValidator;
import com.portfolioy0711.api.typings.EventRouter;

import com.portfolioy0711.api.typings.vo.event.EventTypeEnum;
import lombok.NoArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@NoArgsConstructor
public class EventService {
    private final Map<String, EventRouter> routes = new HashMap<>();

    public EventService addEventRouter(String path, EventRouter router) {
        routes.put(path, router);
        return this;
    }

    public void route(Object body) throws ParseException, JsonProcessingException {
        EventValidator eventValidator = new EventValidator(body);

        String eventType = eventValidator
            .validate("type", EventTypeEnum.getEventTypes())
            .getValueAsType("type");

        this.routes.get(eventType).route(body);
    }
}

