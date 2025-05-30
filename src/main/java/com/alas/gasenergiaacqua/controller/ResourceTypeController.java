package com.alas.gasenergiaacqua.controller;

import com.alas.gasenergiaacqua.dto.ResourceTypeDTO;
import com.alas.gasenergiaacqua.service.ResourceTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/resourceType")
public class ResourceTypeController {

    private final ResourceTypeService resourceTypeService;

    public ResourceTypeController(ResourceTypeService resourceTypeService) {
        this.resourceTypeService = resourceTypeService;
    }

    @GetMapping("/{id}")
    public ResourceTypeDTO get(@PathVariable Integer id) {
        return resourceTypeService.getById(id);
    }

    @GetMapping("/all")
    public List<ResourceTypeDTO> getAll() {
        return resourceTypeService.getAll();
    }
}
