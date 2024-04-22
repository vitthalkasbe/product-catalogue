package org.oms.in.productcatalogue.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oms.in.productcatalogue.entity.Product;
import org.oms.in.productcatalogue.exception.ProductNotFoundException;
import org.oms.in.productcatalogue.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks
    ProductController productController;
    @Mock
    ProductService productService;

    @Test
    public void shouldReturnOKWhenfindAllProducts()
    {
        List<Product> products = Arrays.asList(new Product(1L, "Vim bar", "Soap", 12.99, LocalDateTime.now()));
        Pageable pagging = PageRequest.of(0, 5);
        Mockito.when(productService.findAllProducts(pagging)).thenReturn(products);
        Assertions.assertEquals(HttpStatus.OK,productController.findAllProducts(5,0).getStatusCode());

    }

    @Test
    public void shouldReturn201WhenProductCreated()
    {
        Product product = new Product(1L, "Vim bar", "Soap", 12.99, LocalDateTime.now());
        Mockito.when(productService.saveProduct(product)).thenReturn(product);
        Assertions.assertEquals(HttpStatus.CREATED,productController.saveProduct(product).getStatusCode());
    }

    @Test
    public void shouldReturnOKWhenFindByProductID()
    {
        Product product = new Product(1L, "Vim bar", "Soap", 12.99, LocalDateTime.now());
        Mockito.when(productService.getProductById(1L)).thenReturn(Optional.of(product));
        Assertions.assertEquals(HttpStatus.OK,productController.findProductById(1L).getStatusCode());

    }

    @Test
    public void shouldThrowProductNotFoundExceptionWhenFindByProductID()
    {
        Product product = new Product(1L, "Vim bar", "Soap", 12.99, LocalDateTime.now());
        Mockito.when(productService.getProductById(1L)).thenThrow(ProductNotFoundException.class);
        Assertions.assertThrows(ProductNotFoundException.class,()->productController.findProductById(1L));

    }

    @Test
    public void shouldReturOKWhenProductUpdated()
    {
        Product product = new Product(1L, "Vim bar", "Soap", 12.99, LocalDateTime.now());
        Product savedProduct = new Product(1L, "Vim bar", "Soap", 19.99, LocalDateTime.now());
        Mockito.when(productService.getProductById(1L)).thenReturn(Optional.of(product));
        Mockito.when(productService.saveProduct(savedProduct)).thenReturn(savedProduct);

        Assertions.assertEquals(HttpStatus.OK,productController.updateProduct(1L,product).getStatusCode());
    }

    @Test
    public void shouldThrowProductNotFoundExceptionWhenProductUpdated()
    {
        Product product = new Product(1L, "Vim bar", "Soap", 12.99, LocalDateTime.now());
        Product savedProduct = new Product(1L, "Vim bar", "Soap", 19.99, LocalDateTime.now());
        Mockito.when(productService.getProductById(1L)).thenThrow(ProductNotFoundException.class);
        Assertions.assertThrows(ProductNotFoundException.class,()->productController.updateProduct(1L,product));
    }

    @Test
    public void shouldReturnOKWhenProductDeleted()
    {
        Product product = new Product(1L, "Vim bar", "Soap", 12.99, LocalDateTime.now());
        Mockito.when(productService.getProductById(1L)).thenReturn(Optional.of(product));
        Mockito.doNothing().when(productService).deleteProduct(1L);
        Assertions.assertEquals(HttpStatus.OK,productController.deleteProduct(1L).getStatusCode());
    }

    @Test
    public void shouldThrowProductNotFoundExceptionWhenProductDeleted()
    {
        Product product = new Product(1L, "Vim bar", "Soap", 12.99, LocalDateTime.now());
        Mockito.when(productService.getProductById(1L)).thenThrow(ProductNotFoundException.class);
        Assertions.assertThrows(ProductNotFoundException.class,()->productController.deleteProduct(1L));
    }

}
