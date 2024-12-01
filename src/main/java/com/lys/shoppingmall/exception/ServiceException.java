package com.lys.shoppingmall.exception;

import org.springframework.http.HttpStatusCode;


public abstract class ServiceException extends RuntimeException {
    public abstract HttpStatusCode getHttpStatusCode();
    public abstract String getErrorCode();
    public abstract String getErrorMessage();

    public ServiceException(String message) {
        super(message);
    }
}
