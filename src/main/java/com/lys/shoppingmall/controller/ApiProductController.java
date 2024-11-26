package com.lys.shoppingmall.controller;

import com.lys.shoppingmall.model.order.Order;
import com.lys.shoppingmall.model.product.Product;
import com.lys.shoppingmall.model.request.OrderRequest;
import com.lys.shoppingmall.model.request.ProductRequest;
import com.lys.shoppingmall.model.response.ProductListResponse;
import com.lys.shoppingmall.model.response.ProductResponse;
import com.lys.shoppingmall.service.OrderService;
import com.lys.shoppingmall.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiProductController {
    private final ProductService productService;
    private final OrderService orderService;

    public ApiProductController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/api/products")
    public ProductListResponse getProducts(){
        List<Product> products = productService.getAllProducts();
        List<ProductResponse> productsResponse = products.stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock()))
                .toList();
        return new ProductListResponse(productsResponse);
    }

    @PostMapping("/api/products")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request){
        Product product = productService.addProduct(request);
        ProductResponse newProduct = new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock());
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/api/products/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("productId") int productId, @RequestBody ProductRequest request){
        Product product = productService.updateProduct(productId, request);
        ProductResponse updateProduct = new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock());
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/api/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") int productId){
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/products/{productId}/purchase")
    public ResponseEntity<String> purchaseProduct(@RequestBody OrderRequest request){
        Order order = orderService.addOrder(request);
        if(order.getId() == 0){
            return ResponseEntity.badRequest().body("재고가 부족합니다.");
        }
        return ResponseEntity.ok("구매가 완료되었습니다.");
    }
}
