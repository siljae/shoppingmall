package com.lys.shoppingmall.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class NotFoundException extends ServiceException {
    private final HttpStatusCode httpStatusCode = HttpStatus.NOT_FOUND;
    private final String errorCode = "000000";
    private final String errorMessage = "NotFound";

    public NotFoundException(String message) {
        super(message);
    }
}
