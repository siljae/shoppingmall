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

    // 얘 쓸 곳이 없는데 지울까.. 굳이 필요한가..?
    @GetMapping("/api/products/{productId}")
    public ResponseEntity<ProductResponse> getProductDetail(@PathVariable("productId") int productId){
        ProductResponse productDetail = productService.getById(productId);

        if(productDetail != null){
            return ResponseEntity.ok(productDetail);
        }
        else{
            return ResponseEntity.notFound().build();
        }
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
