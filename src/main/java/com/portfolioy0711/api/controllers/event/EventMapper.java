package com.portfolioy0711.api.controllers.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolioy0711.api.typings.dto.EventDto;
import com.portfolioy0711.api.typings.exception.InvalidRequestException;
import com.portfolioy0711.api.typings.vo.event.EventTypeEnum;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Arrays;

public class EventMapper {
    private JSONObject jsonObject;
    private Object body;

    public EventMapper(Object body) throws JsonProcessingException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        String eventStr = mapper.writeValueAsString(body);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(eventStr);
        this.body = body;
        this.jsonObject = jsonObject;
    }

    public <T extends Object> T getValueAsType(String key) {
       return (T) this.jsonObject.get(key);
    }

    public EventMapper validate(String key, String[] values) throws JsonProcessingException, ParseException {
        if (!jsonObject.containsKey(key)) {
            throw new InvalidRequestException("type must be one of the following values");
        } else {
            String type = (String) jsonObject.get(key);
            Boolean isContains = Arrays.stream(values).anyMatch(v -> v.equals(type));
            if (!isContains)  {
                throw new InvalidRequestException(String.format("type must be one of %s but received \"%s\"", Arrays.toString(values), type));
            }
        }
        return this;
    }

    public <T extends EventDto> T transform(Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(body, type);
    }
}
