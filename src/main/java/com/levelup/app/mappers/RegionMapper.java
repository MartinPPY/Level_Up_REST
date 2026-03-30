package com.levelup.app.mappers;

import com.levelup.app.models.Region;
import com.levelup.app.models.dtos.RegionDto;
import org.mapstruct.Mapper;

/**
 * Mapper para la entidad Region.
 */
@Mapper(componentModel = "spring")
public interface RegionMapper {

    RegionDto toDto(Region region);

    Region toEntity(RegionDto regionDto);
}
