package com.lys.shoppingmall.controller;

import com.lys.shoppingmall.model.order.Order;
import com.lys.shoppingmall.model.product.Product;
import com.lys.shoppingmall.model.request.OrderRequest;
import com.lys.shoppingmall.model.request.ProductRequest;
import com.lys.shoppingmall.model.response.OrderResponse;
import com.lys.shoppingmall.model.response.ProductListResponse;
import com.lys.shoppingmall.model.response.ProductResponse;
import com.lys.shoppingmall.service.ProductService;
import com.lys.shoppingmall.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiProductController {
    private final ProductService productService;
    private final PurchaseService purchaseService;

    @GetMapping("/api/products")
    public ProductListResponse getProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponse> productsResponse = products.stream()
                .map(product -> ProductResponse.valueOf(product.getId(), product.getName(), product.getPrice(), product.getStock()))
                .toList();
        return new ProductListResponse(productsResponse);
    }

    @PostMapping("/api/products")
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        Product product = productService.addProduct(request);
        return ProductResponse.valueOf(product.getId(), product.getName(), product.getPrice(), product.getStock());
    }

    @PutMapping("/api/products/{productId}")
    public ProductResponse updateProduct(@PathVariable("productId") int productId, @RequestBody ProductRequest request) {
        Product product = Product.createProduct(request.getName(), request.getPrice(), request.getStock());
        Product Updatedproduct = productService.updateProduct(productId, product);
        return ProductResponse.valueOf(Updatedproduct.getId(), Updatedproduct.getName(), Updatedproduct.getPrice(), Updatedproduct.getStock());
    }

    @DeleteMapping("/api/products/{productId}")
    public void deleteProduct(@PathVariable("productId") int productId) {
        productService.deleteProduct(productId);
    }

    @PostMapping("/api/products/{productId}/purchase")
    public OrderResponse purchaseProduct(@RequestBody OrderRequest request) {
        Order order = purchaseService.purchase(request.getProductId());
        return OrderResponse.valueOf(order);
    }
}
