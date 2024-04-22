package org.oms.in.productcatalogue.service;

import org.oms.in.productcatalogue.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

     List<Product> findAllProducts(Pageable pageable);
     Optional<Product> getProductById(Long id);
     Product saveProduct(Product product);
     void deleteProduct(Long id);
}
