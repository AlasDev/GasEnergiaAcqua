package com.alas.gasenergiaacqua.controller;

import com.alas.gasenergiaacqua.dto.*;
import com.alas.gasenergiaacqua.filter.ReadingFilter;
import com.alas.gasenergiaacqua.service.ReadingService;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/reading")
public class ReadingController {

    private final ReadingService readingService;

    public ReadingController(ReadingService readingService) {
        this.readingService = readingService;
    }

    @GetMapping("/{id}")
    public ReadingDTO get(@PathVariable UUID id) {
        return readingService.getById(id);
    }

    @GetMapping("/filter")
    public PageDTO<ReadingDTO> getFiltered(Pageable pageable, ReadingFilter filter) {
        return readingService.searchBySpecification(pageable, filter);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage delete(@PathVariable UUID id) {
        return readingService.deleteById(id);
    }

    @PostMapping("/post")
    public ResponseMessage create(@Validated @RequestBody ReadingNewDTO DTO) {
        return readingService.postNew(DTO);
    }

    @PutMapping("/update")
    public ReadingDTO update(@Validated @RequestBody ReadingUpdateDTO DTO) {
        return readingService.updateReading(DTO);
    }
}
