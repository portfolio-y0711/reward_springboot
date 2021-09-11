package com.portfolioy0711.api.controllers.event;

import com.portfolioy0711.api.typings.exception.DuplicateRecordException;
import com.portfolioy0711.api.typings.exception.InvalidRequestException;
import com.portfolioy0711.api.typings.exception.NoRecordException;
import com.portfolioy0711.api.typings.response.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice(assignableTypes = EventController.class)
public class EventControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({ InvalidRequestException.class })
    public HttpEntity<ErrorMessage> invalidRequestException(Exception ex, WebRequest request) {
        ErrorMessage message = ErrorMessage.builder()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
        logger.error("handled invalid request exception");
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ DuplicateRecordException.class })
    public HttpEntity<ErrorMessage> duplicateRecordException(Exception ex, WebRequest request) {
        ErrorMessage message = ErrorMessage.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
        logger.error("handled duplicate record exception");
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ NoRecordException.class })
    public HttpEntity<ErrorMessage> noRecordException(Exception ex, WebRequest request) {
        ErrorMessage message = ErrorMessage.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
        logger.error("handled no record exception");
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
    }
}
