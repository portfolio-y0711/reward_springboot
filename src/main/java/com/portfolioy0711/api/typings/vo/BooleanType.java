package com.portfolioy0711.api.typings.vo;

public enum BooleanType {
    FALSE(0),
    TRUE(1);

    private int value;

    private BooleanType(int value) {
       this.value = value;
    }

    public int getValue() {
        return value;
    }
}
