package com.levelup.app.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.levelup.app.exception.NotFoundException;
import com.levelup.app.models.Category;
import com.levelup.app.models.Product;
import com.levelup.app.models.dtos.ProductDto;
import com.levelup.app.repositories.CategoryRepository;
import com.levelup.app.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    
    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDto findProductDto(String code) {
        System.out.println(code);
        Product productDb = productRepository.findById(code).orElseThrow(
                () -> new NotFoundException("Producto no encontrado!"));

        ProductDto productDto = new ProductDto(code, productDb.getName(), productDb.getPrecio(), productDb.getStock(),
                productDb.getStockCritico(), productDb.getDescription(), productDb.getImage(),
                productDb.getCategory().getId().intValue());

        return productDto;
    }

    @Transactional
    @Override
    public Product save(ProductDto product) {

        Category category = categoryRepository.findById(product.getCategory().longValue()).orElseThrow(
                () -> new NotFoundException("Categoria no encontrada!"));

        Product producto = new Product(product.getCode(), product.getName(),
                product.getPrecio(), product.getStock(), product.getStockCritico(),
                product.getDescription(), product.getImage(), category);

        return productRepository.save(producto);
    }

    @Transactional
    @Override
    public Product editProduct(String code, ProductDto productDto) {
        Product productDb = productRepository.findById(code).orElseThrow(
                () -> new NotFoundException("Producto no encontrado!"));

        Category categoryDb = categoryRepository.findById(productDto.getCategory().longValue()).orElseThrow(
                () -> new NotFoundException("Categoria no encontrada!"));

        productDb.setCode(productDto.getCode());
        productDb.setCategory(categoryDb);
        productDb.setDescription(productDto.getDescription());
        productDb.setName(productDto.getName());
        productDb.setPrecio(productDto.getPrecio());
        productDb.setStock(productDto.getStock());
        productDb.setStockCritico(productDto.getStockCritico());

        return productRepository.save(productDb);
    }

    @Transactional
    @Override
    public void deleteById(String code) {
        Product product = productRepository.findById(code)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productRepository.delete(product);
    }

}