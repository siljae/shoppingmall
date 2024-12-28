package com.lys.shoppingmall.service;

import com.lys.shoppingmall.exception.ProductNotFoundException;
import com.lys.shoppingmall.mapper.ProductMapper;
import com.lys.shoppingmall.model.product.Product;
import com.lys.shoppingmall.model.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductStockRedisService productStockRedisService;

    public List<Product> getAllProducts() {
        return productMapper.getAllProducts();
    }

    public Product getById(int id) {
        Product product = productMapper.getProductById(id);

        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        return product;
    }

    public Product addProduct(ProductRequest request) {
        Product product = Product.createProduct(request.getName(), request.getPrice(), request.getMaxStock());

        productMapper.insertProduct(product);
        System.out.println("상품 추가하려고 왔음: " + product.getId());
        productStockRedisService.updateRedisProductSaleCount(product.getId());

        return product;
    }

    public Product updateProduct(int id, Product product) {
        Product updatedProduct = productMapper.getProductById(id);

        if (updatedProduct == null) throw new ProductNotFoundException(id);
        if (product.getName() != null) updatedProduct.setName(product.getName());
        if (product.getPrice() > 0) updatedProduct.setPrice(product.getPrice());
        if (product.getMaxStock() >= 0) updatedProduct.setMaxStock(product.getMaxStock());

        productMapper.updateProduct(updatedProduct);

        return updatedProduct;
    }

    public void deleteProduct(int id) {
        productMapper.deleteProduct(id);
    }


}
