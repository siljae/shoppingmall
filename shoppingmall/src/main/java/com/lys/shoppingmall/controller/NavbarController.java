package com.lys.shoppingmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavbarController {

    @GetMapping("/products")
    public String showProducts(){
        return "products";
    }

    @GetMapping("/product-detail")
    public String detailProduct(){
        return "product-detail";
    }

    @GetMapping("/add-product")
    public String addProduct(){
        return "add-product";
    }

    @GetMapping("/update-product")
    public String updateProduct(){
        return "update-product";
    }
}
