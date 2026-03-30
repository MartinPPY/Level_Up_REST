package com.levelup.app.mappers;

import com.levelup.app.models.Category;
import com.levelup.app.models.dtos.CategoryDto;
import org.mapstruct.Mapper;

/**
 * Mapper para la entidad Category.
 * Utiliza MapStruct para la conversión bidireccional entre la entidad persistente 
 * y su respectivo Data Transfer Object.
 * Integrado como componente de Spring para facilitar la inyección de dependencias.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * Convierte una entidad persistente a su representación DTO.
     * @param category Entidad origen.
     * @return DTO destino.
     */
    CategoryDto toDto(Category category);

    /**
     * Convierte un DTO a su representación de entidad persistente.
     * @param categoryDto DTO origen.
     * @return Entidad destino.
     */
    Category toEntity(CategoryDto categoryDto);
}
