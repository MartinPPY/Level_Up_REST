package com.levelup.app.models.dtos;

/**
 * Data Transfer Object para la entidad Category.
 * Permite la transferencia de datos de categoría entre capas.
 */
public class CategoryDto {

    private Long id;
    private String name;

    /**
     * Constructor por defecto requerido para serialización y MapStruct.
     */
    public CategoryDto() {
    }

    /**
     * Constructor con parámetros para inicialización rápida.
     * @param id Identificador único de la categoría.
     * @param name Nombre de la categoría.
     */
    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Métodos Getter y Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
