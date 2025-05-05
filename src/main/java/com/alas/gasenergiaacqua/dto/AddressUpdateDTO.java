package com.alas.gasenergiaacqua.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO with the data of Address that can be modified
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressUpdateDTO {
    private UUID id;
    private String streetAddress;
    private String city;
    private String postalCode;
    private String country;
}
