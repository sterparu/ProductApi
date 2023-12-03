package com.example.productapi.exception;

import java.util.UUID;

public class ProductNotFoundException extends ProductException {
    private String id;

    public ProductNotFoundException(String id) {
        super("Product not found with id: " + id);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}