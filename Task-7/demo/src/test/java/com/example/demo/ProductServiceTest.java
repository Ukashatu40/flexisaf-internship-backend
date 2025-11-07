package com.example.demo;

import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductService productService;

    @Test
    void testProductRepository(){
        Product product = new Product("T-shirt", 2000.00, 3);

        when(productRepository.save(product)).thenReturn(product);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName(product.getName());
        productRequest.setPrice(product.getPrice());
        productRequest.setStockQuantity(product.getStockQuantity());

        Product result = productService.createProduct(productRequest);

        assertEquals("T-shirt", result.getName());
        assertEquals(2000.00, result.getPrice());
        assertEquals(3, result.getStockQuantity());

    }

}
