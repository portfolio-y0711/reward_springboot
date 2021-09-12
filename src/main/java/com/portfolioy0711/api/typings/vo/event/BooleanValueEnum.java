package com.portfolioy0711.api.typings.vo.event;

public enum BooleanValueEnum {
    FALSE(0),
    TRUE(1);

    private int value;

    private BooleanValueEnum(int value) {
       this.value = value;
    }

    public int getValue() {
        return value;
    }
}
