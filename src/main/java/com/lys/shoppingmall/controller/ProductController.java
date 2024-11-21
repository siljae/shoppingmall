package com.lys.shoppingmall.controller;

import com.lys.shoppingmall.exception.ProductNotFoundException;
import com.lys.shoppingmall.model.response.ProductResponse;
import com.lys.shoppingmall.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/products")
    public String showProducts() {
        return "products";
    }

    @GetMapping("/products/{productId}")
    public String detailProduct(@PathVariable("productId") int productId, Model model){
        ProductResponse productResponse = productService.getById(productId);
        model.addAttribute("product", productResponse);
        return "product-detail";
    }

    @GetMapping("/add-product")
    public String addProduct() {
        return "add-product";
    }
}
