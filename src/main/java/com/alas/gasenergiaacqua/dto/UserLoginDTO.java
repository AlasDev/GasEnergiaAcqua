package com.alas.gasenergiaacqua.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO expected to be found in login's body
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginDTO {

    @Email
    private String email;

    private String password;
}
