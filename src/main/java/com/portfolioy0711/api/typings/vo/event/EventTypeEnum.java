package com.portfolioy0711.api.typings.vo.event;

import java.util.stream.Stream;

public enum EventTypeEnum {
    REVIEW("REVIEW"),
    BLAR_BLAR("BLAR_BLAR");

    private String eventType;

    EventTypeEnum(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
       return this.eventType;
    }

    public static String[] getEventTypes() {
        return Stream.of(values()).map(e -> e.getEventType()).toArray(String[]::new);
    }

    public static Stream<EventTypeEnum> stream() {
       return Stream.of(EventTypeEnum.values()) ;
    }
}
