package com.alas.gasenergiaacqua.mapper;

import com.alas.gasenergiaacqua.dto.PageDTO;
import com.alas.gasenergiaacqua.dto.UserDTO;
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
        uses = {UtilityMeterMapper.class},
        builder = @Builder(disableBuilder = true))
public interface UserMapper {

    @Mapping(target = "utilityMeters", source = "utilityMeters", qualifiedByName = "toUtilityMetersUuids")
    UserDTO toDto(User user);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "utilityMeters", source = "utilityMeters", qualifiedByName = "toUtilityMetersEntities")
    User toEntity(UserDTO userDto);

    List<UserDTO> toDtos(List<User> users);

    List<User> toEntities(List<UserDTO> userDtos);

    PageDTO<UserDTO> mapToPageDTO(Page<User> page);

    @Named("toUtilityMetersUuid")
    default UUID toUtilityMetersUuid(UtilityMeter entity) {
        return entity.getUuid();
    }

    @Named("toUtilityMetersEntity")
    default UtilityMeter toUtilityMetersEntity(UUID uuid) {
        return UtilityMeter.builder()
                .uuid(uuid)
                .build();
    }

    @Named("toUtilityMetersUuids")
    default List<UUID> toUtilityMetersUuids(List<UtilityMeter> entities) {
        return entities.stream()
                .map(UtilityMeter::getUuid)
                .toList();
    }

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
}