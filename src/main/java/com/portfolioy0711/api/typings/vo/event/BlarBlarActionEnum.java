package com.portfolioy0711.api.typings.vo.event;

import java.util.stream.Stream;

public enum BlarBlarActionEnum {
    A("A"),
    B("B"),
    C("C");

    private String actionType;

    BlarBlarActionEnum(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return this.actionType;
    }

    public static String[] getActionTypes() {
        return Stream.of(values()).map(e -> e.getActionType()).toArray(String[]::new);
    }

    public static Stream<BlarBlarActionEnum> stream() {
        return Stream.of(BlarBlarActionEnum.values());
    }
}
