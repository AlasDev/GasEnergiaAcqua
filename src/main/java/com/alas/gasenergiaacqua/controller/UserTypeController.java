package com.alas.gasenergiaacqua.controller;

import com.alas.gasenergiaacqua.dto.UserTypeDTO;
import com.alas.gasenergiaacqua.service.UserTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/userType")
public class UserTypeController {

    private final UserTypeService userTypeService;

    public UserTypeController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    @GetMapping("/{id}")
    public UserTypeDTO get(@PathVariable Integer id) {
        return userTypeService.getById(id);
    }

    @GetMapping("/all")
    public List<UserTypeDTO> getAll() {
        return userTypeService.getAll();
    }
}
