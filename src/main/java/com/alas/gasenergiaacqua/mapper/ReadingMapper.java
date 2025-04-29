package com.alas.gasenergiaacqua.mapper;

import com.alas.gasenergiaacqua.dto.PageDTO;
import com.alas.gasenergiaacqua.dto.ReadingDTO;
import com.alas.gasenergiaacqua.entity.Reading;
import com.alas.gasenergiaacqua.entity.UtilityMeter;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface ReadingMapper {

    @Mapping(target = "meter", source = "meter", qualifiedByName = "toMeterUuid")
    ReadingDTO toDto(Reading reading);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "meter", source = "meter", qualifiedByName = "toMeterEntity")
    Reading toEntity(ReadingDTO readingDto);

    List<ReadingDTO> toDtos(List<Reading> readings);

    List<Reading> toEntities(List<ReadingDTO> readingDtos);

    PageDTO<ReadingDTO> mapToPageDTO(Page<Reading> page);

    @Named("toMeterEntity")
    default UtilityMeter toMeterEntity(UUID uuid) {
        return UtilityMeter.builder()
                .uuid(uuid)
                .build();
    }

    @Named("toMeterUuid")
    default UUID toMeterUuid(UtilityMeter entity) {
        return entity.getUuid();
    }
}