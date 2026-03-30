package com.levelup.app.mappers;

import com.levelup.app.models.VentaDetalle;
import com.levelup.app.models.dtos.VentaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper para los detalles de venta.
 * Dado que VentaDto representa una línea de detalle en el proceso de compra,
 * este mapper facilita la conversión a la entidad VentaDetalle.
 */
@Mapper(componentModel = "spring")
public interface VentaMapper {

    /**
     * Convierte un VentaDto (proveniente de un request) a la entidad VentaDetalle.
     * Mapea el código de producto y la información del usuario (email se maneja en el servicio).
     */
    @Mapping(source = "code", target = "product.code")
    @Mapping(source = "precio", target = "precio")
    @Mapping(source = "cantidad", target = "cantidad")
    VentaDetalle toDetalleEntity(VentaDto ventaDto);

    /**
     * Convierte una entidad VentaDetalle a su representación DTO.
     */
    @Mapping(source = "product.code", target = "code")
    VentaDto toDto(VentaDetalle detalle);
}
