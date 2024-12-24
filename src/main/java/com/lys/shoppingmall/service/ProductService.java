package com.lys.shoppingmall.service;

import com.lys.shoppingmall.exception.OutOfStockException;
import com.lys.shoppingmall.exception.ProductNotFoundException;
import com.lys.shoppingmall.mapper.ProductMapper;
import com.lys.shoppingmall.model.product.Product;
import com.lys.shoppingmall.model.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final RedisTemplate<String, Object> redisTemplate;

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
        Product product = Product.createProduct(request.getName(), request.getPrice(), request.getStock());

        productMapper.insertProduct(product);

        return product;
    }

    public Product updateProduct(int id, Product product) {
        Product updatedProduct = productMapper.getProductById(id);

        if (updatedProduct == null) throw new ProductNotFoundException(id);
        if (product.getName() != null) updatedProduct.setName(product.getName());
        if (product.getPrice() > 0) updatedProduct.setPrice(product.getPrice());
        if (product.getStock() >= 0) updatedProduct.setStock(product.getStock());

        productMapper.updateProduct(updatedProduct);

        return updatedProduct;
    }

    public void deleteProduct(int id) {
        productMapper.deleteProduct(id);
    }

    public void reduceStock(int productId, int quantity) {
        Integer productStock = getProductStock(productId);

        if (productStock == null) throw new ProductNotFoundException(productId);
        if (productStock < quantity) throw new OutOfStockException(productId);

        redisTemplate.opsForValue().decrement("product:" + productId, quantity);
    }

    public Integer getProductStock(int productId) {
        String key = "product:" + productId;
        redisTemplate.watch(key);

        Integer productStock = (Integer) redisTemplate.opsForValue().get(key);

        if (productStock != null) {
            return productStock;
        }

        try {
            productStock = productMapper.getProductStockById(productId);
            redisTemplate.multi();
            redisTemplate.opsForValue().set(key, productStock);
            redisTemplate.exec();
        } catch (Exception e) {
            redisTemplate.unwatch();
            throw e;
        }
        return productStock;
    }

}
