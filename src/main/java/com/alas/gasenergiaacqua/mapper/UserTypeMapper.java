package com.alas.gasenergiaacqua.mapper;

import com.alas.gasenergiaacqua.dto.UserTypeDTO;
import com.alas.gasenergiaacqua.entity.UserType;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {UtilityMeterTypeMapper.class},
        builder = @Builder(disableBuilder = true))
public interface UserTypeMapper {

    UserTypeDTO mapToDto(UserType entity);

    UserType mapToEntity(UserTypeDTO dto);

    List<UserTypeDTO> mapToDtos(List<UserType> entities);

    List<UserType> mapToEntities(List<UserTypeDTO> dtos);
}
