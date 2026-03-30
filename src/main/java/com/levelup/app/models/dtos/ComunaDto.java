package com.levelup.app.models.dtos;

/**
 * Data Transfer Object para la entidad Comuna.
 * Incluye campos planos para ID de región y nombre de región para simplificar
 * el manejo de las relaciones en el frontend o capa de presentación.
 */
public class ComunaDto {

    private Long id;
    private String name;
    private Long regionId;
    private String regionName;

    /**
     * Constructor por defecto.
     */
    public ComunaDto() {
    }

    /**
     * Constructor con parámetros.
     * @param id Identificador de la comuna.
     * @param name Nombre de la comuna.
     * @param regionId Identificador de la región asociada.
     * @param regionName Nombre de la región asociada.
     */
    public ComunaDto(Long id, String name, Long regionId, String regionName) {
        this.id = id;
        this.name = name;
        this.regionId = regionId;
        this.regionName = regionName;
    }

    // Getters y Setters

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

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
