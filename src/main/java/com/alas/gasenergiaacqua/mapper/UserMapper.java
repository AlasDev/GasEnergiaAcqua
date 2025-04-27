package com.alas.gasenergiaacqua.mapper;

import com.alas.gasenergiaacqua.dto.UserDTO;
import com.alas.gasenergiaacqua.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDto(User user);
    User toEntity(UserDTO userDto);
}