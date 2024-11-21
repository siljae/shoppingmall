package com.lys.shoppingmall.db;

import com.lys.shoppingmall.mapper.ProductMapper;
import com.lys.shoppingmall.model.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@TestPropertySource(properties = "jasypt.encryptor.password=8tYhG6jK")
public class TestDb {

//    private final ProductMapper productMapper;
//
//    public TestDb(ProductMapper productMapper) {
//        this.productMapper = productMapper;
//    }

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testSelectProducts(){
        List<Product> products = productMapper.getAllProducts();
        for(Product product : products){
            System.out.println(product.toString());
        }
    }

    @Test
    public void testAddProduct(){
        Product newProduct = new Product();
        newProduct.setName("돼지감자");
        newProduct.setPrice(1000);
        newProduct.setStock(1000);

        productMapper.insertProduct(newProduct);

        System.out.println("id : " + newProduct.getId());
        Product addedProduct = productMapper.getProductById(newProduct.getId());
        if(addedProduct != null)
            System.out.println(addedProduct.toString());
        else
            System.out.println("제품 등록 실패 : addedProduct가 null입니다.");

    }

    @Test
    public void testByIdProduct(){
        Product idProduct = productMapper.getProductById(99);

        if(idProduct != null)
            System.out.println(idProduct.toString());
        else
            System.out.println("상품과 맞는 ID가 존재 하지 않습니다 : " + idProduct);
    }

    @Test
    public void testUpdateProduct(){
        Product updateProduct = new Product();
        updateProduct.setId(15);
        updateProduct.setName("완도김");
        updateProduct.setPrice(3990);
        updateProduct.setStock(3000);

        productMapper.updateProduct(updateProduct);
        Product selectProduct = productMapper.getProductById(15);

        if(selectProduct != null)
            System.out.println(selectProduct.toString());
        else
            System.out.println("상품 수정 실패 : selectProduct가 null 입니다.");
    }

    @Test
    public void testDeleteProduct(){
        int id = 27;

        Product beforeProduct = productMapper.getProductById(id);

        if(beforeProduct != null)
            System.out.println(beforeProduct.toString());
        else
            System.out.println("상품 조회 실패 : beforeProduct가 없습니다");

        productMapper.deleteProduct(id);
        Product afterProduct = productMapper.getProductById(id);

        if(afterProduct != null)
            System.out.println(afterProduct.toString());
        else
            System.out.println("상품 삭제 완료 : afterProduct가 null 입니다.");
    }
}
