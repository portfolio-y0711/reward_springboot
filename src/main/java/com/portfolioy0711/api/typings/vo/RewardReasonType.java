package com.portfolioy0711.api.typings.vo;

public enum RewardReasonType {
    NEW {
        @Override
        public String toString() {
            return "NEW";
        }
    },
    MOD {
        @Override
        public String toString() {
            return "MOD";
        }
    },
    DEL {
        @Override
        public String toString() {
            return "DEL";
        }
    },
    RED {
        @Override
        public String toString() {
            return "DEL";
        }
    }
}

