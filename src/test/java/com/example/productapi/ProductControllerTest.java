package com.example.productapi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.example.productapi.controller.ProductController;
import com.example.productapi.entity.Product;
import com.example.productapi.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddProduct() throws Exception {
        Product product = new Product(UUID.randomUUID(), "Test Product", 10.0);
        when(productService.save(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));

        verify(productService).save(any(Product.class));
    }
    @Test
    public void testFindProductFound() throws Exception {
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "Test Product", 10.0);
        when(productService.findProductById(productId)).thenReturn(product);

        mockMvc.perform(get("/api/products/" + productId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId.toString()));

        verify(productService).findProductById(productId);
    }

    @Test
    public void testChangeProductPrice() throws Exception {
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "Test Product", 15.0);
        when(productService.changeProductPrice(productId, 15.0)).thenReturn(product);

        mockMvc.perform(put("/api/products/" + productId.toString() + "/price?newPrice=15.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(15.0));

        verify(productService).changeProductPrice(productId, 15.0);
    }
    @Test
    public void testDeleteProduct() throws Exception {
        UUID productId = UUID.randomUUID();

        mockMvc.perform(delete("/api/products/" + productId.toString()))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(productId);
    }
}
