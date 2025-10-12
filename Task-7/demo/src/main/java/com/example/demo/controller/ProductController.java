package com.example.demo.controller;

import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 1. GET /api/products (Retrieve all products)
    // REST Best Practice: Returns 200 OK
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // 2. GET /api/products/{id} (Retrieve a product by ID)
    // REST Best Practice: Returns 200 OK or 404 NOT FOUND (handled by ResourceNotFoundException)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // 3. POST /api/products (Create a new product)
    // REST Best Practice: Returns 201 CREATED
    @PostMapping
    public ResponseEntity<Product> createProduct(
            // @Valid triggers input validation defined in ProductRequest DTO
            @Valid @RequestBody ProductRequest productRequest) {

        Product newProduct = productService.createProduct(productRequest);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    // 4. PUT /api/products/{id} (Update an existing product)
    // REST Best Practice: Returns 200 OK or 404 NOT FOUND
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest productRequest) {

        Product updatedProduct = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(updatedProduct);
    }
}