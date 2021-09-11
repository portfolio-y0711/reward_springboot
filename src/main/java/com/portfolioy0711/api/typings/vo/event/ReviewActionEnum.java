package com.portfolioy0711.api.typings.vo.event;

import java.util.stream.Stream;

public enum ReviewActionEnum {
    ADD("ADD"),
    MOD("MOD"),
    DELETE("DELETE");

    private String actionType;

    ReviewActionEnum(String actionType) {
        this.actionType = actionType;
    }

    public String getEventType() {
        return this.actionType;
    }

    public static String[] getActionTypes() {
        return Stream.of(values()).map(e -> e.getEventType()).toArray(String[]::new);
    }

    public static Stream<ReviewActionEnum> stream() {
        return Stream.of(ReviewActionEnum.values());
    }
}
