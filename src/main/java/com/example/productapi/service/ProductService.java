package com.example.productapi.service;

import com.example.productapi.entity.Product;
import com.example.productapi.exception.ProductException;
import com.example.productapi.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ProductService {

    @Autowired
    protected ProductRepository productRepository;

    public Product save(Product product) {
        try {
            productRepository.save(product);
        } catch (Exception e) {
            log.error("Error in the process of saveing the product " + product.getName());
            e.printStackTrace();
        }

        return product;
    }

    public Product findProductById(UUID id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            return null;
        }

    }

    public Product changeProductPrice(UUID id, double price) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setPrice(price);
            try {
                productRepository.save(product);
                return product;
            } catch (ProductException e) {
                log.error("there was a problem updating the product with the id " + id);
                return null;
            }
        } else {
            return null;
        }
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
        System.out.println("Product " + id + "was deleted");
    }
}
