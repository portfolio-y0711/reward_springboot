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

    public String getReviewActionType() {
        return this.actionType;
    }

    public static String[] getReviewActionTypes() {
        return Stream.of(values()).map(e -> e.getReviewActionType()).toArray(String[]::new);
    }

    public static Stream<ReviewActionEnum> stream() {
        return Stream.of(ReviewActionEnum.values());
    }
}
