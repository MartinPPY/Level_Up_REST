package com.levelup.app.mappers;

import com.levelup.app.models.Product;
import com.levelup.app.models.dtos.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper para la entidad Product.
 * Gestiona la conversión entre Product y ProductDto, incluyendo el ID de categoría.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    /**
     * Convierte una entidad Product a ProductDto.
     * El campo category (Long ID) se mapea al campo category (Integer) del DTO.
     */
    @Mapping(source = "category.id", target = "category")
    ProductDto toDto(Product product);

    /**
     * Convierte un ProductDto a la entidad Product.
     * Mapea el ID de categoría del DTO al objeto Category de la entidad.
     */
    @Mapping(source = "category", target = "category.id")
    Product toEntity(ProductDto productDto);
}
