package com.levelup.app.services;

import com.levelup.app.exception.NotFoundException;
import com.levelup.app.mappers.ProductMapper;
import com.levelup.app.models.Category;
import com.levelup.app.models.Product;
import com.levelup.app.models.dtos.ProductDto;
import com.levelup.app.repositories.CategoryRepository;
import com.levelup.app.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDto productDto;
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        product = new Product();
        product.setCode("P001");
        product.setName("Laptop");
        product.setPrecio(1000.0);
        product.setStock(10L);
        product.setCategory(category);

        productDto = new ProductDto();
        productDto.setCode("P001");
        productDto.setName("Laptop");
        productDto.setPrecio(1000.0);
        productDto.setStock(10L);
        productDto.setCategory(1);
    }

    @Test
    @DisplayName("Should return a list of product DTOs")
    void shouldReturnListOfProductDtos() {
        // Given
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productMapper.toDto(any(Product.class))).thenReturn(productDto);

        // When
        List<ProductDto> result = productService.findAll();

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getCode()).isEqualTo("P001");
        verify(productRepository).findAll();
    }

    @Test
    @DisplayName("Should find product by code and return DTO")
    void shouldFindProductByCode() {
        // Given
        when(productRepository.findById("P001")).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(productDto);

        // When
        ProductDto result = productService.findProductDto("P001");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCode()).isEqualTo("P001");
    }

    @Test
    @DisplayName("Should throw NotFoundException when product code is not found")
    void shouldThrowExceptionWhenProductNotFound() {
        // Given
        when(productRepository.findById("P999")).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> productService.findProductDto("P999"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Producto no encontrado!");
    }

    @Test
    @DisplayName("Should save a new product successfully")
    void shouldSaveProductSuccessfully() {
        // Given
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productMapper.toEntity(any(ProductDto.class))).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDto);

        // When
        ProductDto savedProduct = productService.save(productDto);

        // Then
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getCode()).isEqualTo("P001");
        verify(productRepository).save(any(Product.class));
    }
}
