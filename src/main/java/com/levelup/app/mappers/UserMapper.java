package com.levelup.app.mappers;

import com.levelup.app.models.User;
import com.levelup.app.models.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper para la entidad User.
 * Gestiona la conversión entre User y UserDto, incluyendo fechas y relaciones de ID.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convierte una entidad User a UserDto.
     * Mapea relaciones de Comuna y Role a sus respectivos IDs.
     * Formatea la fecha de nacimiento a String.
     */
    @Mapping(source = "comuna.id", target = "comunaId")
    @Mapping(source = "role.id", target = "role")
    @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyy-MM-dd")
    UserDto toDto(User user);

    /**
     * Convierte un UserDto a la entidad User.
     * Mapea IDs a sus respectivas entidades.
     * Convierte el String de fecha de nacimiento a LocalDate.
     */
    @Mapping(source = "comunaId", target = "comuna.id")
    @Mapping(source = "role", target = "role.id")
    @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyy-MM-dd")
    User toEntity(UserDto userDto);
}
