package com.alas.gasenergiaacqua.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO expected to be received when creating a new address
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressNewDTO {
    private String streetAddress;
    private String city;
    private String postalCode;
    private String country;
}
