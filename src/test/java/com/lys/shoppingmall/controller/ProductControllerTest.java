package com.lys.shoppingmall.controller;

import com.lys.shoppingmall.model.response.ProductResponse;
import com.lys.shoppingmall.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("상품 상세 정보 가져오기")
    public void testDetailProduct() throws Exception{
        int productId = 1;
        ProductResponse productResponse = new ProductResponse(productId, "닭가슴살", 100, 10);

        when(productService.getById(productId)).thenReturn(productResponse);

        mockMvc.perform(get("/products/{productId}", productId)
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("product-detail"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", productResponse));

        verify(productService, times(1)).getById(productId);

        System.out.println("productResponse : " + productResponse.toString());
    }
}
