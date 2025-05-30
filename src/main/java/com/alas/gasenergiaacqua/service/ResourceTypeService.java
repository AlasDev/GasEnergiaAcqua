package com.alas.gasenergiaacqua.service;

import com.alas.gasenergiaacqua.dto.ResourceTypeDTO;
import com.alas.gasenergiaacqua.mapper.ResourceTypeMapper;
import com.alas.gasenergiaacqua.repository.ResourceTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ResourceTypeService {

    private final ResourceTypeMapper resourceTypeMapper;
    private final ResourceTypeRepository resourceTypeRepository;

    public ResourceTypeService(ResourceTypeMapper resourceTypeMapper, ResourceTypeRepository resourceTypeRepository) {
        this.resourceTypeMapper = resourceTypeMapper;
        this.resourceTypeRepository = resourceTypeRepository;
    }

    /**
     * @param id uuid of the Resource Type you want to get
     * @return a DTO if found
     */
    public ResourceTypeDTO getById(Integer id) {
        return resourceTypeMapper.mapToDto(resourceTypeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot find!\nNo resource type was found with id: " + id)));
    }

    /**
     * @return a list of DTO
     */
    public List<ResourceTypeDTO> getAll() {
        return resourceTypeMapper.mapToDtos(resourceTypeRepository.findAll());
    }
}
