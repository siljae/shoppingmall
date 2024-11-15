package com.lys.shoppingmall.repository;

import com.lys.shoppingmall.mapper.ProductMapper;
import com.lys.shoppingmall.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository{
    private final ProductMapper productMapper;

    public ProductRepositoryImpl(ProductMapper productMapper){
        this.productMapper = productMapper;
    }


    @Override
    public List<Product> findAll() {
        return productMapper.getAllProducts();
    }

    @Override
    public Product findById(int id) {
        return productMapper.getProductById(id);
    }

    @Override
    public void save(Product product) {
        productMapper.insertProduct(product);
    }

    @Override
    public void update(Product product) {
        productMapper.updateProduct(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteProduct(id);
    }
}
