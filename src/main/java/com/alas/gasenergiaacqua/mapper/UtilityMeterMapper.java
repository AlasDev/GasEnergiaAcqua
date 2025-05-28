package com.alas.gasenergiaacqua.mapper;

import com.alas.gasenergiaacqua.dto.PageDTO;
import com.alas.gasenergiaacqua.dto.UtilityMeterDTO;
import com.alas.gasenergiaacqua.dto.UtilityMeterSummaryDTO;
import com.alas.gasenergiaacqua.entity.Reading;
import com.alas.gasenergiaacqua.entity.UtilityMeter;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        uses = {AddressMapper.class, UserMapper.class, ReadingMapper.class},
        builder = @Builder(disableBuilder = true))
public interface UtilityMeterMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "addressId", source = "address.id")
    @Mapping(target = "readingsIds", source = "readings", qualifiedByName = "toReadingsIds")
    @Mapping(target = "resourceTypeId", source = "resourceType.id")
    @Mapping(target = "utilityMeterTypeId", source = "utilityMeterType.id")
    UtilityMeterDTO mapToDto(UtilityMeter entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "installationDate", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "address.id", source = "addressId")
    @Mapping(target = "readings", source = "readingsIds", qualifiedByName = "toReadingsEntities")
    @Mapping(target = "resourceType.id", source = "resourceTypeId")
    @Mapping(target = "utilityMeterType.id", source = "utilityMeterTypeId")
    UtilityMeter mapToEntity(UtilityMeterDTO dto);

    @Mapping(target = "resourceTypeId", source = "resourceType.id")
    UtilityMeterSummaryDTO mapToUtilityMeterSummaryDto(UtilityMeter entity);

    List<UtilityMeterSummaryDTO> mapToUtilityMeterSummaryDtos(List<UtilityMeter> entities);

    List<UtilityMeterDTO> mapToDtos(List<UtilityMeter> entities);

    List<UtilityMeter> mapToEntities(List<UtilityMeterDTO> dtos);

    PageDTO<UtilityMeterDTO> mapToPageDTO(Page<UtilityMeter> page);

    @Named("toReadingsIds")
    default List<UUID> toReadingsIds(List<Reading> entities) {
        return entities.stream().map(Reading::getId).toList();
    }

    @Named("toReadingsEntities")
    default List<Reading> toReadingsEntities(List<UUID> ids) {
        List<Reading> entities = new ArrayList<>();
        for (UUID id : ids) {
            entities.add(Reading.builder()
                    .id(id)
                    .build());
        }
        return entities;
    }
}