package com.alas.gasenergiaacqua.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO returned when you need to show a user or a list of users without showing all their data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSummaryDTO {

    private UUID uuid;

    private String nome;

    private String cognome;

    @Email
    private String email;
}
