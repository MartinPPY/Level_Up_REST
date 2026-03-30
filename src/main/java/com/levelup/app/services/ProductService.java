package com.levelup.app.services;

import java.util.List;

import com.levelup.app.models.dtos.ProductDto;

public interface ProductService {
    

    List<ProductDto> findAll();

    ProductDto findProductDto(String code);

    ProductDto save (ProductDto product);
    
    void deleteById(String code);

    ProductDto editProduct(String code, ProductDto productDto);

}