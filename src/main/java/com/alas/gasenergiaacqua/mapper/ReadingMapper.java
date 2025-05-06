package com.alas.gasenergiaacqua.mapper;

import com.alas.gasenergiaacqua.dto.PageDTO;
import com.alas.gasenergiaacqua.dto.ReadingDTO;
import com.alas.gasenergiaacqua.entity.Reading;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface ReadingMapper {

    @Mapping(target = "utilityMeterId", source = "utilityMeter.id")
    ReadingDTO mapToDto(Reading entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "utilityMeter.id", source = "utilityMeterId")
    Reading mapToEntity(ReadingDTO dto);

    List<ReadingDTO> mapToDtos(List<Reading> entities);

    List<Reading> mapToEntities(List<ReadingDTO> dtos);

    PageDTO<ReadingDTO> mapToPageDTO(Page<Reading> page);
}