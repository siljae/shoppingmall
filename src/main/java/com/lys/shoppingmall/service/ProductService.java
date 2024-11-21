package com.lys.shoppingmall.service;

import com.lys.shoppingmall.exception.ProductNotFoundException;
import com.lys.shoppingmall.mapper.ProductMapper;
import com.lys.shoppingmall.model.product.Product;
import com.lys.shoppingmall.model.request.ProductRequest;
import com.lys.shoppingmall.model.response.ProductListResponse;
import com.lys.shoppingmall.model.response.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public ProductListResponse getAllProducts(){
        List<Product> products = productMapper.getAllProducts();
        List<ProductResponse> productsResponse = products.stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock()))
                .collect(Collectors.toList());
        return new ProductListResponse(productsResponse);
    }

    public ProductResponse getById(int id){
        Product product = productMapper.getProductById(id);

        if(product == null){
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }

        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock());
    }

    public ProductResponse addProduct(ProductRequest request){
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        productMapper.insertProduct(product);

        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock());
    }

    @Transactional
    public ProductResponse updateProduct(int id, ProductRequest request){
        Product product = productMapper.getProductById(id);

        if(product == null){
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }

        if(request.getName() != null)
            product.setName(request.getName());

        if(request.getPrice() > 0)
            product.setPrice(request.getPrice());

        if(request.getStock() > 0)
            product.setStock(request.getStock());

        productMapper.updateProduct(product);

        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock());
    }

    @Transactional
    public void deleteProduct(int id){
        productMapper.deleteProduct(id);
    }
}
