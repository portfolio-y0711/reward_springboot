package com.portfolioy0711.api.services.review;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.portfolioy0711.api.controllers.event.EventMapper;
import com.portfolioy0711.api.typings.ActionHandler;
import com.portfolioy0711.api.typings.EventRouter;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import com.portfolioy0711.api.typings.vo.event.ReviewActionEnum;
import lombok.NoArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@NoArgsConstructor
public class ReviewEventActionRouter implements EventRouter {

    Map<String, ActionHandler> routes = new HashMap<>();

    @Override
    public EventRouter addRoute(String path, ActionHandler handler) {
       this.routes.put(path, handler);
       return this;
    }

    public Map<String, ActionHandler> getRoute() {
        return this.routes;
    }

    @Override
    public void route (Object body) throws JsonProcessingException, ParseException {
        EventMapper eventMapper = new EventMapper(body);
        eventMapper.validate("action", ReviewActionEnum.getActionTypes());
        ReviewEventDto eventInfo = (ReviewEventDto) eventMapper.transform(ReviewEventDto.class);
        String action = eventInfo.getAction();
        routes.get(action).handleEvent(eventInfo);
    }
}
