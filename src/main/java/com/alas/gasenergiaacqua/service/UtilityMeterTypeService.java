package com.alas.gasenergiaacqua.service;

import com.alas.gasenergiaacqua.dto.UtilityMeterTypeDTO;
import com.alas.gasenergiaacqua.mapper.UtilityMeterTypeMapper;
import com.alas.gasenergiaacqua.repository.UtilityMeterTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UtilityMeterTypeService {

    private final UtilityMeterTypeRepository utilityMeterTypeRepository;
    private final UtilityMeterTypeMapper utilityMeterTypeMapper;

    public UtilityMeterTypeService(UtilityMeterTypeRepository utilityMeterTypeRepository, UtilityMeterTypeMapper utilityMeterTypeMapper) {
        this.utilityMeterTypeRepository = utilityMeterTypeRepository;
        this.utilityMeterTypeMapper = utilityMeterTypeMapper;
    }

    /**
     * @param id uuid of the Utility Meter Type you want to get
     * @return a DTO if found
     */
    public UtilityMeterTypeDTO getById(Integer id) {
        return utilityMeterTypeMapper.mapToDto(utilityMeterTypeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot find!\nNo utility meter type was found with id: " + id)));
    }

    /**
     * @return a list of DTO
     */
    public List<UtilityMeterTypeDTO> getAll() {
        return utilityMeterTypeMapper.mapToDtos(utilityMeterTypeRepository.findAll());
    }
}
