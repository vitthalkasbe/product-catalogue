package org.oms.in.productcatalogue.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oms.in.productcatalogue.entity.Product;
import org.oms.in.productcatalogue.repository.ProductRepository;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;


    @Test
    public void findAllProducts_should_return_all_products()
    {
        Pageable page = PageRequest.of(0, 3);
        List<Product> products = Arrays.asList(new Product(1L, "some product1", "clothing", 234.22, LocalDateTime.now()),
                new Product(2L, "some product2", "electronics", 2224.22, LocalDateTime.now()),
                new Product(3L, "some product3", "clothing", 234.22, LocalDateTime.now()));

        PageImpl<Product> productsPage = new PageImpl<>(products, page, products.size());
        Mockito.when(productRepository.findAll(page)).thenReturn(productsPage);

        List<Product> allProducts = productService.findAllProducts(page);
        Assertions.assertEquals(3,allProducts.size());
    }

    @Test
    public void when_getProductByID_called_should_return_product()
    {
        Product product = new Product(2L, "some product2", "electronics", 2224.22, LocalDateTime.now());
        Mockito.when(productRepository.findById(2L)).thenReturn(Optional.of(product));
        Product actualProduct = productService.getProductById(2L).get();

        Assertions.assertEquals(2L,actualProduct.getId());
        Assertions.assertEquals("some product2",actualProduct.getName());
        Assertions.assertEquals(2224.22,actualProduct.getPrice());
        Assertions.assertEquals("electronics",actualProduct.getProductType());

    }

    @Test
    public void when_getProductByID_called_should_return_empty_option_provided_ID_is_not_found()
    {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertEquals(Optional.empty(),productService.getProductById(1L));
    }

    @Test
    public void  should_save_product_in_db_when_saveProduct_called()
    {
        Product product = new Product(null, "some product2", "electronics", 2224.22, LocalDateTime.now());
        Product savedProduct = new Product(2L, "some product2", "electronics", 2224.22, LocalDateTime.now());

        Mockito.when(productRepository.save(product)).thenReturn(savedProduct);

        Product savedProductInDB = productService.saveProduct(product);
        Assertions.assertEquals(2L,savedProductInDB.getId());

    }

    @Test
    public void shouldDeleteProductWhen_deleteProduct_called()
    {
        Mockito.doNothing().when(productRepository).deleteById(2l);
        productService.deleteProduct(2l);
        Mockito.verify(productRepository,Mockito.times(1)).deleteById(2l);
    }


}
