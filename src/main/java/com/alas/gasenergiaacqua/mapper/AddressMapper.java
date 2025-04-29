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

    @Mapping(target = "utilityMeters", source = "utilityMeters", qualifiedByName = "toUtilityMetersUuids")
    AddressDTO toDto(Address address);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "utilityMeters", source = "utilityMeters", qualifiedByName = "toUtilityMetersEntities")
    Address toEntity(AddressDTO addressDto);

    List<AddressDTO> toDtos(List<Address> addresses);

    List<Address> toEntities(List<AddressDTO> addressDtoList);

    PageDTO<AddressDTO> mapToPageDTO(Page<Address> page);

    @Named("toUtilityMetersEntities")
    default List<UtilityMeter> toUtilityMetersEntities(List<UUID> uuids) {
        List<UtilityMeter> entities = new ArrayList<>();
        for (UUID uuid : uuids) {
            UtilityMeter entity = UtilityMeter.builder()
                    .uuid(uuid)
                    .build();
            entities.add(entity);
        }
        return entities;
    }

    @Named("toUtilityMetersUuids")
    default List<UUID> toUtilityMetersUuids(List<UtilityMeter> entities) {
        return entities.stream()
                .map(UtilityMeter::getUuid)
                .toList();
    }
}