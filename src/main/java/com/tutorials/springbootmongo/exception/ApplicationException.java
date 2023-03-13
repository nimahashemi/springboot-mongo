package com.tutorials.springbootmongo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplicationException extends RuntimeException {
    private int code;
    private Object result;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
        this.code = 4303;
    }

    public ApplicationException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ApplicationException(int code, String message, Object result) {
        super(message);
        this.code = code;
        this.result = result;
    }


    public int getCode() {
        return code;
    }

    public Object getResult() {
        return result;
    }
}
