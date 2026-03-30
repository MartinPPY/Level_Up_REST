package com.levelup.app.mappers;

import com.levelup.app.models.Comuna;
import com.levelup.app.models.dtos.ComunaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper para la entidad Comuna.
 * Realiza la conversión bidireccional entre la entidad Comuna y ComunaDto.
 * Facilita el mapeo de relaciones anidadas (Region) hacia campos planos del DTO.
 */
@Mapper(componentModel = "spring")
public interface ComunaMapper {

    /**
     * Convierte la entidad Comuna a ComunaDto.
     * Mapea los ID y Nombres de la Región asociada a los campos planos del DTO.
     * @param comuna Entidad origen.
     * @return DTO destino.
     */
    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.name", target = "regionName")
    ComunaDto toDto(Comuna comuna);

    /**
     * Convierte ComunaDto a la entidad Comuna persistente.
     * La asociación de la entidad Region debe ser manejada adicionalmente o ignorada 
     * si se desea persistencia pura basada en IDs (dependiendo de la lógica de servicio).
     * @param comunaDto DTO origen.
     * @return Entidad destino.
     */
    @Mapping(source = "regionId", target = "region.id")
    @Mapping(source = "regionName", target = "region.name")
    Comuna toEntity(ComunaDto comunaDto);
}
