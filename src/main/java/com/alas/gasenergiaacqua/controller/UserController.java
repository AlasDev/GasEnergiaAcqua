package com.alas.gasenergiaacqua.controller;

import com.alas.gasenergiaacqua.dto.*;
import com.alas.gasenergiaacqua.filter.UserFilter;
import com.alas.gasenergiaacqua.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDTO get(@PathVariable UUID id) {
        return userService.getById(id);
    }

    @GetMapping("/filter")
    public PageDTO<UserSummaryDTO> getFiltered(Pageable pageable, UserFilter filter) {
        return userService.searchBySpecification(pageable, filter);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage delete(@PathVariable UUID id) {
        return userService.deleteById(id);
    }

    @PostMapping("/post")
    public ResponseMessage create(@RequestBody UserRegisterDTO DTO) {
        return userService.postNew(DTO);
    }

    @PutMapping("/update")
    public UserDTO update(@RequestBody UserUpdateDTO DTO) {
        return userService.updateUser(DTO);
    }
}
