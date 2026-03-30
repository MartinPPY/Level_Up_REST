package com.levelup.app.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.levelup.app.exception.NotFoundException;
import com.levelup.app.models.Category;
import com.levelup.app.models.Product;
import com.levelup.app.models.dtos.ProductDto;
import com.levelup.app.mappers.ProductMapper;
import com.levelup.app.repositories.CategoryRepository;
import com.levelup.app.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    @Override
    public List<ProductDto> findAll() {
        List<Product> products = (List<Product>) productRepository.findAll();
        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDto findProductDto(String code) {
        Product productDb = productRepository.findById(code).orElseThrow(
                () -> new NotFoundException("Producto no encontrado!"));

        return productMapper.toDto(productDb);
    }

    @Transactional
    @Override
    public ProductDto save(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategory().longValue()).orElseThrow(
                () -> new NotFoundException("Categoria no encontrada!"));

        Product product = productMapper.toEntity(productDto);
        product.setCategory(category);

        return productMapper.toDto(productRepository.save(product));
    }

    @Transactional
    @Override
    public ProductDto editProduct(String code, ProductDto productDto) {
        productRepository.findById(code).orElseThrow(
                () -> new NotFoundException("Producto no encontrado!"));

        Category categoryDb = categoryRepository.findById(productDto.getCategory().longValue()).orElseThrow(
                () -> new NotFoundException("Categoria no encontrada!"));

        Product product = productMapper.toEntity(productDto);
        product.setCode(code); 
        product.setCategory(categoryDb);

        return productMapper.toDto(productRepository.save(product));
    }

    @Transactional
    @Override
    public void deleteById(String code) {
        Product product = productRepository.findById(code)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productRepository.delete(product);
    }

}