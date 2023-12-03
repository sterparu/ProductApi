package com.example.productapi.controller;

import com.example.productapi.entity.Product;
import com.example.productapi.exception.ProductNotFoundException;
import com.example.productapi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product createdProduct = productService.save(product);
        log.info("Product saved successfully: {}", createdProduct.getId());
        return ResponseEntity.ok(createdProduct);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> findProduct(@PathVariable String id) {
        Product product = productService.findProductById(UUID.fromString(id));
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            log.error("Product not found with ID: {}", id);
            throw new ProductNotFoundException(id);
        }
    }

    @PutMapping("/{id}/price")
    public ResponseEntity<Product> changeProductPrice(@PathVariable String id, @RequestParam("newPrice") double newPrice) {
        Product updatedProduct = productService.changeProductPrice(UUID.fromString(id), newPrice);
        if (updatedProduct != null) {
            log.info("Product price updated for ID: {}", id);
            return ResponseEntity.ok(updatedProduct);
        } else {
            log.error("Product not found for update with ID: {}", id);
            throw new ProductNotFoundException(id);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(UUID.fromString(id));
        log.info("Product deleted successfully: {}", id);
    }


}