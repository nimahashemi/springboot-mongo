package com.tutorials.springbootmongo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotAcceptableException extends RuntimeException {
    private String causeMessage;

    public NotAcceptableException() {
        super("Request rejected");
    }

    public NotAcceptableException(String cause) {
        super("Request rejected");
        this.causeMessage = cause;
    }

    public String getCauseMessage() {
        return causeMessage;
    }
}
