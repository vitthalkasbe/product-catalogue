package org.oms.in.productcatalogue.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oms.in.productcatalogue.entity.Product;
import org.oms.in.productcatalogue.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerMockMVCTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void testFindAllProducts() throws Exception
//    {
//        List<Product> products = Arrays.asList(new Product(1L, "Vim bar", "Soap", 12.99, LocalDateTime.now()));
//        Pageable pagging = PageRequest.of(0, 5);
//        Mockito.when(productService.findAllProducts(pagging)).thenReturn(products);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/products").with(httpBasic()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(1));
//
//    }
}
