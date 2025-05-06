package com.alas.gasenergiaacqua.service;

import com.alas.gasenergiaacqua.dto.*;
import com.alas.gasenergiaacqua.entity.UtilityMeter;
import com.alas.gasenergiaacqua.exception.ElementAlreadyPresentException;
import com.alas.gasenergiaacqua.filter.UtilityMeterFilter;
import com.alas.gasenergiaacqua.mapper.UtilityMeterMapper;
import com.alas.gasenergiaacqua.repository.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UtilityMeterService {

    private final UtilityMeterRepository utilityMeterRepository;
    private final UtilityMeterMapper utilityMeterMapper;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ResourceTypeRepository resourceTypeRepository;
    private final UtilityMeterTypeRepository utilityMeterTypeRepository;


    public UtilityMeterService(UtilityMeterRepository utilityMeterRepository, UtilityMeterMapper utilityMeterMapper, UserRepository userRepository, AddressRepository addressRepository, ResourceTypeRepository resourceTypeRepository, UtilityMeterTypeRepository utilityMeterTypeRepository) {
        this.utilityMeterRepository = utilityMeterRepository;
        this.utilityMeterMapper = utilityMeterMapper;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.resourceTypeRepository = resourceTypeRepository;
        this.utilityMeterTypeRepository = utilityMeterTypeRepository;
    }

    /**
     * @param id uuid of the UtilityMeter you want to get
     * @return a DTO if found
     */
    public UtilityMeterDTO getById(UUID id){
        return utilityMeterMapper.mapToDto(utilityMeterRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot find!\nNo utility meter was found with id: " + id)));
    }

    /**
     * @param params filters applied to the search
     * @param pageable pageable
     * @return a pageDTO containing a list of DTO
     */
    public PageDTO<UtilityMeterSummaryDTO> searchBySpecification(Pageable pageable, UtilityMeterFilter params) {
        return utilityMeterMapper.mapToPageDTO(utilityMeterRepository.findAll(params.toSpecification(), pageable));
    }

    /**
     * Deletes a UtilityMeter with given uuid
     * @param id uuid of the UtilityMeter which is going to be deleted
     * @return a ResponseMessage
     */
    public ResponseMessage deleteById(UUID id) {
        utilityMeterRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot delete!\nNo utility meter was found with id: " + id));
        utilityMeterRepository.deleteById(id);

        return ResponseMessage.builder()
                .timestamp(Instant.now())
                .message("UtilityMeter deleted successfully")
                .build();
    }

    /**
     * @param utilityMeterDTO info needed to create a new utilityMeter
     * @return a ResponseMessage
     */
    public ResponseMessage postNew(UtilityMeterNewDTO utilityMeterDTO) {

        if (userRepository.existsByEmail(utilityMeterDTO.getServicePointIdentifier())) {
            throw new ElementAlreadyPresentException("Cannot register a new utility meter!\nService Point identifier " + utilityMeterDTO.getServicePointIdentifier() + " is already in use");
        }

        UtilityMeter newUtilityMeter = UtilityMeter.builder()
                .user(userRepository.findById(utilityMeterDTO.getUserId())
                        .orElseThrow(() -> new NoSuchElementException("Cannot create new utility meter!\nNo user was found")))
                .address(addressRepository.findById(utilityMeterDTO.getAddressId())
                        .orElseThrow(() -> new NoSuchElementException("Cannot create new utility meter!\nNo address was found")))
                .resourceType(resourceTypeRepository.findById(utilityMeterDTO.getResourceTypeId())
                        .orElseThrow(() -> new NoSuchElementException("Cannot create new utility meter!\nNo resource type was found")))
                .utilityMeterType(utilityMeterTypeRepository.findById(utilityMeterDTO.getUtilityMeterTypeId())
                        .orElseThrow(() -> new NoSuchElementException("Cannot create new utility meter!\nNo utility meter type was found")))
                .servicePointIdentifier(utilityMeterDTO.getServicePointIdentifier())
                .serialNumber(utilityMeterDTO.getSerialNumber())
                .meterName(utilityMeterDTO.getMeterName())
                .isActive(utilityMeterDTO.getIsActive())
                .build();
        utilityMeterRepository.save(newUtilityMeter);

        return ResponseMessage.builder()
                .timestamp(Instant.now())
                .message("New utilityMeter registered successfully")
                .build();
    }

    /**
     * @param utilityMeterDTO the changes you want to apply
     * @return the updated UtilityMeter
     */
    public UtilityMeterDTO updateUtilityMeter(UtilityMeterUpdateDTO utilityMeterDTO) {
        UtilityMeter utilityMeter = utilityMeterRepository.findById(utilityMeterDTO.getId()).orElseThrow(() -> new NoSuchElementException("Cannot update!\nNo utility meter was found"));

        if (utilityMeterDTO.getUserId() != null) {
            utilityMeter.setUser(userRepository.findById(utilityMeterDTO.getUserId())
                    .orElseThrow(() -> new NoSuchElementException("Cannot update!\nNo user was found")));
        }
        if (utilityMeterDTO.getAddressId() != null) {
            utilityMeter.setAddress(addressRepository.findById(utilityMeterDTO.getAddressId())
                    .orElseThrow(() -> new NoSuchElementException("Cannot update!\nNo address was found")));
        }
        if (utilityMeterDTO.getResourceTypeId() != null) {
            utilityMeter.setResourceType(resourceTypeRepository.findById(utilityMeterDTO.getResourceTypeId())
                    .orElseThrow(() -> new NoSuchElementException("Cannot update!\nNo resource type was found")));
        }
        if (utilityMeterDTO.getUtilityMeterTypeId() != null) {
            utilityMeter.setUtilityMeterType(utilityMeterTypeRepository.findById(utilityMeterDTO.getUtilityMeterTypeId())
                    .orElseThrow(() -> new NoSuchElementException("Cannot update!\nNo utility meter type was found")));
        }
        if (utilityMeterDTO.getServicePointIdentifier() != null) {
            utilityMeter.setServicePointIdentifier(utilityMeterDTO.getServicePointIdentifier());
        }
        if (utilityMeterDTO.getSerialNumber() != null) {
            utilityMeter.setSerialNumber(utilityMeterDTO.getSerialNumber());
        }
        if (utilityMeterDTO.getMeterName() != null && !utilityMeterDTO.getMeterName().trim().isEmpty()) {
            utilityMeter.setMeterName(utilityMeterDTO.getMeterName());
        }
        if (utilityMeterDTO.getIsActive() != null) {
            utilityMeter.setIsActive(utilityMeterDTO.getIsActive());
        }

        return utilityMeterMapper.mapToDto(utilityMeterRepository.save(utilityMeter));
    }
}
