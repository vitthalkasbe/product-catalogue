package org.oms.in.productcatalogue.repository;

import org.oms.in.productcatalogue.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
   // Optional<List<Product>> findAllByPage(Pageable pageable);
}

