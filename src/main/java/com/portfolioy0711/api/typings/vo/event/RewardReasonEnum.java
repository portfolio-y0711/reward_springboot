package com.portfolioy0711.api.typings.vo.event;

import java.util.stream.Stream;

public enum RewardReasonEnum {
    NEW("NEW"),
    MOD("MOD"),
    DEL("DEL"),
    RED("RED");

    private String rewardReasonType;

    RewardReasonEnum(String reawardReasonType) {
        this.rewardReasonType = reawardReasonType;
    }

    public String getRewardReasonType() {
        return this.rewardReasonType;
    }

    public static String[] getRewardReasonTypes() {
        return Stream.of(values()).map(e -> e.getRewardReasonType()).toArray(String[]::new);
    }

    public static Stream<RewardReasonEnum> stream() {
        return Stream.of(RewardReasonEnum.values());
    }
}

