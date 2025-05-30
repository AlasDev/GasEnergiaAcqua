package com.alas.gasenergiaacqua.controller;

import com.alas.gasenergiaacqua.auth.JwtTokenProvider;
import com.alas.gasenergiaacqua.dto.*;
import com.alas.gasenergiaacqua.filter.UtilityMeterFilter;
import com.alas.gasenergiaacqua.service.UtilityMeterService;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/utilityMeter")
public class UtilityMeterController {

    private final UtilityMeterService utilityMeterService;
    private final JwtTokenProvider jwtTokenProvider;

    public UtilityMeterController(UtilityMeterService utilityMeterService, JwtTokenProvider jwtTokenProvider) {
        this.utilityMeterService = utilityMeterService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/{id}")
    public UtilityMeterDTO get(@PathVariable UUID id) {
        return utilityMeterService.getById(id);
    }

    @GetMapping("/filter")
    public PageDTO<UtilityMeterDTO> getFiltered(Pageable pageable, UtilityMeterFilter filter) {
        return utilityMeterService.searchBySpecification(pageable, filter);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage delete(@PathVariable UUID id) {
        return utilityMeterService.deleteById(id);
    }

    @PostMapping("/post")
    public ResponseMessage create(@Validated @RequestBody UtilityMeterNewDTO DTO) {
        return utilityMeterService.postNew(DTO);
    }

    @PutMapping("/update")
    public UtilityMeterDTO update(@Validated @RequestBody UtilityMeterUpdateDTO DTO) {
        return utilityMeterService.updateUtilityMeter(DTO);
    }

    @GetMapping("/my-utility-meters")
    public PageDTO<UtilityMeterDTO> get(@RequestHeader("Authorization") String token,
                                               Pageable pageable,
                                               UtilityMeterFilter filter) {
        filter.setUserId(jwtTokenProvider.extractIdFromClaims(token));
        return utilityMeterService.searchBySpecification(pageable, filter);
    }
}
