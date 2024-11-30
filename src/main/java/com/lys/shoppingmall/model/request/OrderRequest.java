package com.lys.shoppingmall.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class OrderRequest {
    private int productId;
    private int quantity;
}
