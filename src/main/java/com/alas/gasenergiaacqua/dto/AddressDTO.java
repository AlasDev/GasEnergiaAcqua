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
public class AddressDTO {
    private UUID uuid;
    private String streetAddress;
    private String city;
    private String postalCode;
    private String country;

    private LocalDateTime createdAt;

    private List<UUID> utilityMeters;
}