package com.example.demo.service;


import com.example.demo.dto.ProductRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 1. GET (All)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 2. GET (By ID)
    public Product getProductById(Long id) {
        // Uses the custom ResourceNotFoundException
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    // 3. POST (Create)
    public Product createProduct(ProductRequest request) {
        Product product = new Product(
                request.getName(),
                request.getPrice(),
                request.getStockQuantity()
        );
        return productRepository.save(product);
    }

    // 4. PUT (Update)
    public Product updateProduct(Long id, ProductRequest request) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Update fields based on request DTO
        existingProduct.setName(request.getName());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setStockQuantity(request.getStockQuantity());

        return productRepository.save(existingProduct);
    }
}