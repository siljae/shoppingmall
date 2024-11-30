package com.lys.shoppingmall.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class OrderNotFoundException extends NotFoundException{
    private final HttpStatusCode httpStatusCode = HttpStatus.NOT_FOUND;
    private final String errorCode = "000002";
    private final String errorMessage;

    public OrderNotFoundException(int orderId) {
        super("Order with ID " + orderId + " not found.");
        errorMessage = "order not found (" + orderId + ")";
    }
}
