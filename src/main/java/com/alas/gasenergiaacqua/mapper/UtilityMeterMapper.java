package com.alas.gasenergiaacqua.mapper;

import com.alas.gasenergiaacqua.dto.UtilityMeterDTO;
import com.alas.gasenergiaacqua.entity.UtilityMeter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UtilityMeterMapper {

    UtilityMeterMapper INSTANCE = Mappers.getMapper(UtilityMeterMapper.class);

    UtilityMeterDTO toDto(UtilityMeter utilityMeter);
    UtilityMeter toEntity(UtilityMeterDTO utilityMeterDto);
}