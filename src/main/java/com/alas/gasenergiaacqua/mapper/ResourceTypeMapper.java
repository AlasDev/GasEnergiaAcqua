package com.alas.gasenergiaacqua.mapper;

import com.alas.gasenergiaacqua.dto.ResourceTypeDTO;
import com.alas.gasenergiaacqua.entity.ResourceType;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface ResourceTypeMapper {

    ResourceTypeDTO mapToDto(ResourceType entity);

    ResourceType mapToEntity(ResourceTypeDTO dto);
}
