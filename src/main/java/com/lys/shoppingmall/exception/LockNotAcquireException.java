package com.lys.shoppingmall.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class LockNotAcquireException extends NotAcquireException {
    private final HttpStatusCode httpStatusCode = HttpStatus.LOCKED;
    private final String errorCode = "001001";
    private final String errorMessage;

    public LockNotAcquireException(int productId) {
        super("Product with ID " + productId + " is Lock Not Acquire.");
        errorMessage = "Lock Not Acquire (" + productId + ")";
    }

    public LockNotAcquireException(int productId, Exception e) {
        super("Product with ID " + productId + " is Lock Not Acquire, " + e);
        errorMessage = "Lock Not Acquire (" + productId + "), " + e;
    }
}
