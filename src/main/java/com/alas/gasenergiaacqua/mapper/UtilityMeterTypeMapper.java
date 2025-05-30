package com.alas.gasenergiaacqua.mapper;

import com.alas.gasenergiaacqua.dto.UtilityMeterTypeDTO;
import com.alas.gasenergiaacqua.entity.UtilityMeterType;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface UtilityMeterTypeMapper {

    UtilityMeterTypeDTO mapToDto(UtilityMeterType entity);

    UtilityMeterType mapToEntity(UtilityMeterTypeDTO dto);

    List<UtilityMeterTypeDTO> mapToDtos(List<UtilityMeterType> entities);

    List<UtilityMeterType> mapToEntities(List<UtilityMeterTypeDTO> dtos);
}
