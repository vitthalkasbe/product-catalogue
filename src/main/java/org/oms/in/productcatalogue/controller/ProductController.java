package org.oms.in.productcatalogue.controller;

import io.swagger.annotations.ApiOperation;
import org.oms.in.productcatalogue.entity.Product;
import org.oms.in.productcatalogue.exception.ProductNotFoundException;
import org.oms.in.productcatalogue.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    @ApiOperation(value = "Fetches list of Products",notes = "It returns list of  products avaialble in db",response = List.class)
    public ResponseEntity<List<Product>> findAllProducts(@RequestParam(name = "pageSize",defaultValue = "5",required = false) Integer pageSize,@RequestParam(name = "page",defaultValue = "0",required = false) Integer page) {
        Pageable pagging = PageRequest.of(page, pageSize);

        List<Product> allProducts = productService.findAllProducts(pagging);
        allProducts.forEach(e->
                {
                    WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findProductById(e.getId()));
                    e.add(linkBuilder.withRel("product-details"));
                }
        );

        return ResponseEntity.ok(allProducts);
    }


    @GetMapping("/products/{id}")
    @ApiOperation(value = "Find Product By ID",notes = "It updates product in db",response = Product.class)
    public ResponseEntity<Product> findProductById(@PathVariable("id") Long id) {
        Optional<Product> optionalProduct = productService.getProductById(id);
        if (optionalProduct.isEmpty())
            throw new ProductNotFoundException("Product is not found with ID=" + id);

        WebMvcLinkBuilder linkBuilder=WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findAllProducts(5,0));
        Product product = optionalProduct.get();
        product.add(linkBuilder.withRel("all-products"));
        return ResponseEntity.ok(product);
    }

    @PostMapping("/products")
    @ApiOperation(value = "Create Product",notes = "It creates product in db",response = Product.class)
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid Product product) {
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.created(UriComponentsBuilder.fromPath("/products/{id}").build(savedProduct.getId())).build();
    }

    @PutMapping("/products/{id}")
    @ApiOperation(value = "Update Product",notes = "It updates product in db",response = Product.class)
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody @Valid Product product) {
        Optional<Product> optionalProduct = productService.getProductById(id);
        if (optionalProduct.isEmpty())
            throw new ProductNotFoundException("Product is not found with ID=" + id);

        optionalProduct.get().setId(id);
        optionalProduct.get().setName(product.getName());
        optionalProduct.get().setPrice(product.getPrice());

        Product savedProduct = productService.saveProduct(optionalProduct.get());
        return ResponseEntity.ok(savedProduct);
    }

    @DeleteMapping("/products/{id}")
    @ApiOperation(value = "Delete Product",notes = "It deletes product in db",response = Void.class)
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id) {
        Optional<Product> optionalProduct = productService.getProductById(id);
        if (optionalProduct.isEmpty())
            throw new ProductNotFoundException("Product is not found with ID=" + id);

        productService.deleteProduct(id);
        return ResponseEntity.ok("Product Deleted");
    }

}
