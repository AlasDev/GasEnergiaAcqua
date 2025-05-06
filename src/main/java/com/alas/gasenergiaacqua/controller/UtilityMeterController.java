package com.alas.gasenergiaacqua.controller;

import com.alas.gasenergiaacqua.dto.*;
import com.alas.gasenergiaacqua.filter.ReadingFilter;
import com.alas.gasenergiaacqua.filter.UtilityMeterFilter;
import com.alas.gasenergiaacqua.service.ReadingService;
import com.alas.gasenergiaacqua.service.UtilityMeterService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/utilitymeter")
public class UtilityMeterController {

    private final UtilityMeterService utilityMeterService;

    public UtilityMeterController(UtilityMeterService utilityMeterService) {
        this.utilityMeterService = utilityMeterService;
    }

    @GetMapping("/{id}")
    public UtilityMeterDTO get(@PathVariable UUID id) {
        return utilityMeterService.getById(id);
    }

    @GetMapping("/filter")
    public PageDTO<UtilityMeterSummaryDTO> getFiltered(Pageable pageable, UtilityMeterFilter filter) {
        return utilityMeterService.searchBySpecification(pageable, filter);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage delete(@PathVariable UUID id) {
        return utilityMeterService.deleteById(id);
    }

    @PostMapping("/post")
    public ResponseMessage create(@RequestBody UtilityMeterNewDTO DTO) {
        return utilityMeterService.postNew(DTO);
    }

    @PutMapping("/update")
    public UtilityMeterDTO update(@RequestBody UtilityMeterUpdateDTO DTO) {
        return utilityMeterService.updateUtilityMeter(DTO);
    }
}
