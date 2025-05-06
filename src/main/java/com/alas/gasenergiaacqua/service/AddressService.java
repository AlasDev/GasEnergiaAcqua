package com.alas.gasenergiaacqua.service;

import com.alas.gasenergiaacqua.dto.*;
import com.alas.gasenergiaacqua.entity.Address;
import com.alas.gasenergiaacqua.filter.AddressFilter;
import com.alas.gasenergiaacqua.mapper.AddressMapper;
import com.alas.gasenergiaacqua.repository.AddressRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    /**
     * @param id uuid of the Address you want to get
     * @return a DTO if found
     * @throws NoSuchElementException exception thrown when an Address with specified id is not found
     */
    public AddressDTO getById(UUID id) {
        return addressMapper.mapToDto(addressRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot find!\nNo address was found with id: " + id)));
    }

    /**
     * @param params filters applied to the search
     * @param pageable pageable
     * @return a pageDTO containing a list of DTO
     */
    public PageDTO<AddressDTO> searchBySpecification(Pageable pageable, AddressFilter params) {
        return addressMapper.mapToPageDTO(addressRepository.findAll(params.toSpecification(), pageable));
    }

    /**
     * Deletes an Address with given uuid
     * @param id uuid of the Address which is going to be deleted
     * @return a ResponseMessage
     * @throws NoSuchElementException exception thrown when an Address with specified id is not found
     */
    public ResponseMessage deleteById(UUID id) {
        addressRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot delete!\nNo address was found with id: " + id));
        addressRepository.deleteById(id);

        return ResponseMessage.builder()
                .timestamp(Instant.now())
                .message("Address deleted successfully")
                .build();
    }

    /**
     * @param addressDTO info needed to create a new address
     * @return a ResponseMessage
     */
    public ResponseMessage postNew(AddressNewDTO addressDTO) {

        Address newAddress = Address.builder()
                .streetAddress(addressDTO.getStreetAddress())
                .city(addressDTO.getCity())
                .postalCode(addressDTO.getPostalCode())
                .country(addressDTO.getCountry())
                .build();
        addressRepository.save(newAddress);

        return ResponseMessage.builder()
                .timestamp(Instant.now())
                .message("New address registered successfully")
                .build();
    }

    /**
     * @param addressDTO the changes you want to apply
     * @return the updated Address
     * @throws NoSuchElementException exception thrown when an Address with same id as the dto is not found
     */
    public AddressDTO updateAddress(AddressUpdateDTO addressDTO) {
        Address address = addressRepository.findById(addressDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("Cannot update!\nNo address was found"));

        if (addressDTO.getStreetAddress() != null && !addressDTO.getStreetAddress().trim().isEmpty()) {
            address.setStreetAddress(addressDTO.getStreetAddress());
        }
        if (addressDTO.getCity() != null && !addressDTO.getCity().trim().isEmpty()) {
            address.setCity(addressDTO.getCity());
        }
        if (addressDTO.getPostalCode() != null && !addressDTO.getPostalCode().trim().isEmpty()) {
            address.setPostalCode(addressDTO.getPostalCode());
        }
        if (addressDTO.getCountry() != null && !addressDTO.getCountry().trim().isEmpty()) {
            address.setCountry(addressDTO.getCountry());
        }

        return addressMapper.mapToDto(addressRepository.save(address));
    }
}
