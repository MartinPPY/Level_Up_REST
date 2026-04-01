package com.levelup.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelup.app.models.dtos.ProductDto;
import com.levelup.app.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
@org.springframework.test.context.ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto();
        productDto.setCode("P001");
        productDto.setName("Laptop");
        productDto.setPrecio(1000.0);
        productDto.setStock(10L);
        productDto.setCategory(1);
    }

    @Test
    @DisplayName("Should return all products when authorized")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldReturnAllProducts() throws Exception {
        // Given
        when(productService.findAll()).thenReturn(List.of(productDto));

        // When & Then
        mockMvc.perform(get("/api/v1/productos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("P001"))
                .andExpect(jsonPath("$[0].name").value("Laptop"));
    }

    @Test
    @DisplayName("Should return a product by code when authorized")
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldReturnProductByCode() throws Exception {
        // Given
        when(productService.findProductDto("P001")).thenReturn(productDto);

        // When & Then
        mockMvc.perform(get("/api/v1/productos/P001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("P001"));
    }

    @Test
    @DisplayName("Should create a new product when admin")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldCreateProduct() throws Exception {
        // Given
        when(productService.save(any(ProductDto.class))).thenReturn(productDto);

        // When & Then
        mockMvc.perform(post("/api/v1/productos")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("P001"));
    }

    @Test
    @DisplayName("Should return 401 when unauthorized access")
    void shouldReturn401WhenUnauthorized() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/productos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
