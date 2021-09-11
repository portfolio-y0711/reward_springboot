package com.portfolioy0711.api.typings.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;

    @Builder
    public ErrorMessage(int statusCode, Date timestamp, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
    }
}


