package com.lys.shoppingmall.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class OutOfStockException extends OutOfException{
    private final HttpStatusCode httpStatusCode = HttpStatus.BAD_REQUEST;
    private final String errorCode = "000101";
    private final String errorMessage;

    public OutOfStockException(int productId) {
        super("Product with ID " + productId + " has insufficient stock.");
        errorMessage = "Out of stock for product: " + productId;
    }
}
