package com.lys.shoppingmall.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class NotAcquireException extends ServiceException {
    private final HttpStatusCode httpStatusCode = HttpStatus.LOCKED;
    private final String errorCode = "001000";
    private final String errorMessage = "NotAcquire";

    public NotAcquireException(String message) {
        super(message);
    }
}
