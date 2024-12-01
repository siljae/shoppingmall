package com.lys.shoppingmall.service;

import com.lys.shoppingmall.exception.OutOfStockException;
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
        Product product = productMapper.getProductByIdForUpdate(productId);

        if (product == null) throw new ProductNotFoundException(productId);
        if (product.getStock() < quantity) throw new OutOfStockException(product.getId());

        product.setStock(product.getStock() - quantity);

        productMapper.updateProductStock(product.getId(), product.getStock());
    }
}
