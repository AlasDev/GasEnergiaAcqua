package com.alas.gasenergiaacqua.mapper;

import com.alas.gasenergiaacqua.dto.AddressDTO;
import com.alas.gasenergiaacqua.dto.PageDTO;
import com.alas.gasenergiaacqua.entity.Address;
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
        builder = @Builder(disableBuilder = true), uses = {UtilityMeterMapper.class})
public interface AddressMapper {

    @Mapping(target = "utilityMetersIds", source = "utilityMeters", qualifiedByName = "toUtilityMetersIds")
    AddressDTO mapToDto(Address entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "utilityMeters", source = "utilityMetersIds", qualifiedByName = "toUtilityMetersEntities")
    Address mapToEntity(AddressDTO dto);

    List<AddressDTO> mapToDtos(List<Address> entities);

    List<Address> mapToEntities(List<AddressDTO> dtos);

    PageDTO<AddressDTO> mapToPageDTO(Page<Address> page);

    @Named("toUtilityMetersEntities")
    default List<UtilityMeter> toUtilityMetersEntities(List<UUID> ids) {
        List<UtilityMeter> entities = new ArrayList<>();
        for (UUID id : ids) {
            UtilityMeter entity = UtilityMeter.builder()
                    .id(id)
                    .build();
            entities.add(entity);
        }
        return entities;
    }

    @Named("toUtilityMetersIds")
    default List<UUID> toUtilityMetersIds(List<UtilityMeter> entities) {
        return entities.stream()
                .map(UtilityMeter::getId)
                .toList();
    }
}