package com.alas.gasenergiaacqua.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO with the data of User that can be modified
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDTO {

    private UUID uuid;

    private String name;

    private String surname;

    @Email
    private String email;

    private String password;
}
