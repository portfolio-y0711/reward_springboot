package com.portfolioy0711.api.typings;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;

public interface EventRouter {
    void route(Object event) throws JsonProcessingException, ParseException;
    EventRouter addRoute(String path, ActionHandler handler);
}


