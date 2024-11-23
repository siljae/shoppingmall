package com.lys.shoppingmall.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class ProductNotFoundException extends NotFoundException {
    private final HttpStatusCode httpStatusCode = HttpStatus.NOT_FOUND;
    private final String errorCode = "000001";
    private final String errorMessage;

    public ProductNotFoundException(int productId){
        super("Product with ID " + productId + " is not found.");
        errorMessage = "product not found (" + productId + ")";
    }
}
