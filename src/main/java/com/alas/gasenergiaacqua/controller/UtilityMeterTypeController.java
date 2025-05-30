package com.alas.gasenergiaacqua.controller;

import com.alas.gasenergiaacqua.dto.UtilityMeterTypeDTO;
import com.alas.gasenergiaacqua.service.UtilityMeterTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/utilityMeterType")
public class UtilityMeterTypeController {

    private final UtilityMeterTypeService utilityMeterTypeService;

    public UtilityMeterTypeController(UtilityMeterTypeService utilityMeterTypeService) {
        this.utilityMeterTypeService = utilityMeterTypeService;
    }

    @GetMapping("/{id}")
    public UtilityMeterTypeDTO get(@PathVariable Integer id) {
        return utilityMeterTypeService.getById(id);
    }

    @GetMapping("/all")
    public List<UtilityMeterTypeDTO> getAll() {
        return utilityMeterTypeService.getAll();
    }
}
