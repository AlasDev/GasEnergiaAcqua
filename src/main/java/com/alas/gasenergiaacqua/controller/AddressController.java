package com.alas.gasenergiaacqua.controller;

import com.alas.gasenergiaacqua.dto.*;
import com.alas.gasenergiaacqua.filter.AddressFilter;
import com.alas.gasenergiaacqua.service.AddressService;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{id}")
    public AddressDTO get(@PathVariable UUID id) {
        return addressService.getById(id);
    }

    @GetMapping("/filter")
    public PageDTO<AddressDTO> getFiltered(Pageable pageable, AddressFilter filter) {
        return addressService.searchBySpecification(pageable, filter);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage delete(@PathVariable UUID id) {
        return addressService.deleteById(id);
    }

    @PostMapping("/post")
    public ResponseMessage create(@Validated @RequestBody AddressNewDTO DTO) {
        return addressService.postNew(DTO);
    }

    @PutMapping("/update")
    public AddressDTO update(@Validated @RequestBody AddressUpdateDTO DTO) {
        return addressService.updateAddress(DTO);
    }
}
