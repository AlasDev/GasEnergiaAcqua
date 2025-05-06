package com.alas.gasenergiaacqua.mapper;

import com.alas.gasenergiaacqua.dto.PageDTO;
import com.alas.gasenergiaacqua.dto.UserDTO;
import com.alas.gasenergiaacqua.dto.UserSummaryDTO;
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

    @Mapping(target = "utilityMetersIds", source = "utilityMeters", qualifiedByName = "toUtilityMetersIds")
    @Mapping(target = "userTypeId", source = "userType.id")
    UserDTO mapToDto(User entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "utilityMeters", source = "utilityMetersIds", qualifiedByName = "toUtilityMetersEntities")
    @Mapping(target = "userType.id", source = "userTypeId")
    User mapToEntity(UserDTO dto);

    UserSummaryDTO mapToUserSummaryDto(User entity);

    List<UserSummaryDTO> mapToUserSummaryDtos(List<User> entities);

    List<UserDTO> mapToDtos(List<User> entities);

    List<User> mapToEntities(List<UserDTO> dtos);

    PageDTO<UserSummaryDTO> mapToPageDTO(Page<User> page);

    @Named("toUtilityMetersIds")
    default List<UUID> toUtilityMetersIds(List<UtilityMeter> entities) {
        return entities.stream()
                .map(UtilityMeter::getId)
                .toList();
    }

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
}