package com.portfolioy0711.api.config;

import com.portfolioy0711.api.typings.exception.EntityNotFoundException;
import com.portfolioy0711.api.typings.response.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@ControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<ErrorMessage> entityNotFoundException(Exception ex, WebRequest webRequest) {
        ErrorMessage message = ErrorMessage
                .builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(webRequest.getDescription(false))
                .build();
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }
}
