package com.lys.shoppingmall.service;

import com.lys.shoppingmall.exception.OutOfStockException;
import com.lys.shoppingmall.exception.ProductNotFoundException;
import com.lys.shoppingmall.mapper.OrderMapper;
import com.lys.shoppingmall.mapper.ProductMapper;
import com.lys.shoppingmall.model.product.Product;
import com.lys.shoppingmall.model.request.OrderRequest;
import com.lys.shoppingmall.model.request.ProductRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<Product> getAllProducts(){
        return productMapper.getAllProducts();
    }

    @Transactional
    public Product getById(int id){
        Product product = productMapper.getProductById(id);

        if(product == null){
            throw new ProductNotFoundException(id);
        }
        return product;
    }

    @Transactional
    public Product addProduct(ProductRequest request){
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        productMapper.insertProduct(product);

        return product;
    }

    @Transactional
    public Product updateProduct(int id, ProductRequest request){
        Product product = productMapper.getProductById(id);

        if(product == null){
            throw new ProductNotFoundException(id);
        }

        if(request.getName() != null)
            product.setName(request.getName());

        if(request.getPrice() > 0)
            product.setPrice(request.getPrice());

        if(request.getStock() >= 0)
            product.setStock(request.getStock());

        productMapper.updateProduct(product);

        return product;
    }

    @Transactional
    public void deleteProduct(int id){
        productMapper.deleteProduct(id);
    }

    @Transactional
    public void reduceStock(OrderRequest request){
        Product product = productMapper.getProductById(request.getProductId());

        if(product == null) throw new ProductNotFoundException(request.getProductId());
        if(product.getStock() < request.getQuantity()) throw new OutOfStockException(product.getId());

        product.setStock(product.getStock() - request.getQuantity());

        productMapper.updateProductStock(product);
    }

   @Transactional(propagation = Propagation.REQUIRES_NEW)
   public void restoreStock(int productId, int quantity){
        Product product = productMapper.getProductById(productId);

        if(product == null) throw new ProductNotFoundException(productId);
        if(quantity < 0) throw new IllegalArgumentException("복구할 수량은 0보다 커야 합니다");

       product.setStock(product.getStock() + quantity);

       productMapper.updateProductStock(product);
    }
}
