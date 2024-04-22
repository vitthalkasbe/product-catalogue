package org.oms.in.productcatalogue.service;


import org.oms.in.productcatalogue.entity.Product;
import org.oms.in.productcatalogue.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements  ProductService{

    @Autowired
    private ProductRepository repository;


    @Override
    public List<Product> findAllProducts(Pageable pageable) {
        List<Product> productList = repository.findAll(pageable).stream().collect(Collectors.toList());
        return repository.findAll(pageable).stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}
