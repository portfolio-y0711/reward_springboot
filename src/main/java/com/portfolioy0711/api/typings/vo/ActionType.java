package com.portfolioy0711.api.typings.vo;

public enum ActionType {
    ADD {
        @Override
        public String toString() {
            return "ADD";
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
    }
}
