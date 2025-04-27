package com.alas.gasenergiaacqua.mapper;

import com.alas.gasenergiaacqua.dto.AddressDTO;
import com.alas.gasenergiaacqua.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDTO toDto(Address address);
    Address toEntity(AddressDTO addressDto);
}