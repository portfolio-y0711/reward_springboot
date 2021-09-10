package com.portfolioy0711.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolioy0711.api.services.blarblar.BlarBlarEventHandler;
import com.portfolioy0711.api.services.review.ReviewEventActionRouter;
import com.portfolioy0711.api.typings.dto.BlarBlarEventDto;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private ApplicationContext context;

    public EventService(ApplicationContext context) {
        this.context = context;
    }

    public void route (String eventStr) throws ParseException, JsonProcessingException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(eventStr);
        ObjectMapper objectMapper = new ObjectMapper();

        if (jsonObject.containsKey("type") && jsonObject.containsKey("action")) {
            String type = (String) jsonObject.get("type");
            switch(type) {
                case "REVIEW":
                    ReviewEventDto reviewEvent = (ReviewEventDto) objectMapper.readValue(eventStr, new TypeReference<ReviewEventDto>(){});
                    System.out.println("!!!!");
                    ReviewEventActionRouter reviewEventHandler = context.getBean(ReviewEventActionRouter.class);
                    System.out.println(reviewEventHandler);
                    reviewEventHandler.route(reviewEvent);
                    break;
                case "BlarBlar":
                    BlarBlarEventDto blarblarEvent = (BlarBlarEventDto) objectMapper.readValue(eventStr, new TypeReference<BlarBlarEventDto>(){});
                    BlarBlarEventHandler blarblarEventHandler = context.getBean(BlarBlarEventHandler.class);
                    blarblarEventHandler.route(blarblarEvent);
                    break;
            }
        }
    }
}

