package com.lys.shoppingmall.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class OutOfException extends ServiceException{
    private final HttpStatusCode httpStatusCode = HttpStatus.BAD_REQUEST;
    private final String errorCode = "000100";
    private final String errorMessage = "OutOf";

    public OutOfException(String message) {
        super(message);
    }
}
