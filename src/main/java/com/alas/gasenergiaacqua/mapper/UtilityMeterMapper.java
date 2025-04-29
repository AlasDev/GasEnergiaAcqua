package com.alas.gasenergiaacqua.mapper;

import com.alas.gasenergiaacqua.dto.PageDTO;
import com.alas.gasenergiaacqua.dto.UtilityMeterDTO;
import com.alas.gasenergiaacqua.entity.Address;
import com.alas.gasenergiaacqua.entity.Reading;
import com.alas.gasenergiaacqua.entity.User;
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

    @Mapping(target = "user", source = "user", qualifiedByName = "toUserUuid")
    @Mapping(target = "address", source = "address", qualifiedByName = "toAddressUuid")
    @Mapping(target = "readings", source = "readings", qualifiedByName = "toReadingsUuids")
    UtilityMeterDTO toDto(UtilityMeter utilityMeter);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "installationDate", ignore = true)
    @Mapping(target = "user", source = "user", qualifiedByName = "toUserEntity")
    @Mapping(target = "address", source = "address", qualifiedByName = "toAddressEntity")
    @Mapping(target = "readings", source = "readings", qualifiedByName = "toReadingsEntities")
    UtilityMeter toEntity(UtilityMeterDTO utilityMeterDto);

    List<UtilityMeterDTO> toDtos(List<UtilityMeter> utilityMeterList);

    List<UtilityMeter> toEntities(List<UtilityMeterDTO> utilityMeterDTOList);

    PageDTO<UtilityMeterDTO> mapToPageDTO(Page<UtilityMeter> page);

    @Named("toUserEntity")
    default User toUserEntity(UUID uuid) {
        return User.builder()
                .uuid(uuid)
                .build();
    }

    @Named("toUserUuid")
    default UUID toUserUuid(User entity) {
        return entity.getUuid();
    }

    @Named("toAddressEntity")
    default Address toAddressEntity(UUID uuid) {
        return Address.builder()
                .uuid(uuid)
                .build();
    }

    @Named("toAddressUuid")
    default UUID toAddressUuid(Address entity) {
        return entity.getUuid();
    }

    @Named("toReadingsUuids")
    default List<UUID> toReadingsUuids(List<Reading> entities) {
        return entities.stream().map(Reading::getUuid).toList();
    }

    @Named("toReadingsEntities")
    default List<Reading> toReadingsEntities(List<UUID> uuids) {
        List<Reading> entities = new ArrayList<>();
        for (UUID uuid : uuids) {
            entities.add(Reading.builder()
                    .uuid(uuid)
                    .build());
        }
        return entities;
    }
}