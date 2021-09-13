package com.portfolioy0711.api.typings.vo.event;

import java.util.stream.Stream;

public enum RewardOperationEnum {
    ADD("ADD"),
    SUB("SUB");

    private String rewardOperationType;

    RewardOperationEnum(String rewardOperationType) {
        this.rewardOperationType = rewardOperationType;
    }

    public String getRewardType() {
        return this.rewardOperationType;
    }

    public static String[] getRewardOperationTypes() {
        return Stream.of(values()).map(e -> e.getRewardType()).toArray(String[]::new);
    }

    public static Stream<RewardOperationEnum> stream() {
        return Stream.of(RewardOperationEnum.values());
    }
}
