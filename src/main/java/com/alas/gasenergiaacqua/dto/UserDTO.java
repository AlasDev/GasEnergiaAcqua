package com.alas.gasenergiaacqua.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private UUID uuid;
    private String name;
    private String surname;
    private String email;
    private String passwordHash;
    private String userType;

    private LocalDateTime createdAt;
    private List<UUID> utilityMeters;
}