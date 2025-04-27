package com.alas.gasenergiaacqua.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilityMeterDTO {
    private Integer id;
    private Integer userId;
    private Integer addressId;
    private String resourceType;
    private String inputMethod;
    private String servicePointIdentifier;
    private String serialNumber;
    private String meterName;
    private Boolean isActive;
}