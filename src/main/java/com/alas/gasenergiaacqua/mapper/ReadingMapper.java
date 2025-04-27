package com.alas.gasenergiaacqua.mapper;

import com.alas.gasenergiaacqua.dto.ReadingDTO;
import com.alas.gasenergiaacqua.entity.Reading;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReadingMapper {

    ReadingMapper INSTANCE = Mappers.getMapper(ReadingMapper.class);

    ReadingDTO toDto(Reading reading);
    Reading toEntity(ReadingDTO readingDto);
}