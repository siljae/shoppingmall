package com.lys.shoppingmall.controller;

import com.lys.shoppingmall.model.response.ProductListResponse;
import com.lys.shoppingmall.model.request.ProductRequest;
import com.lys.shoppingmall.model.response.ProductResponse;
import com.lys.shoppingmall.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiProductController {
    private final ProductService productService;

    public ApiProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public ProductListResponse getProducts(){
        return productService.getAllProducts();
    }

    @PostMapping("/api/products")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request){
        ProductResponse newProduct = productService.addProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/api/products/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("productId") int productId, @RequestBody ProductRequest request){
        ProductResponse updateProduct = productService.updateProduct(productId, request);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/api/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") int productId){
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
