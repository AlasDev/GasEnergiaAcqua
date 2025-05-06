package com.alas.gasenergiaacqua.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilityMeterNewDTO {
    private UUID userId;
    private UUID addressId;

    private Integer resourceTypeId;
    private Integer utilityMeterTypeId;

    private String servicePointIdentifier;
    private String serialNumber;
    private String meterName;
    private Boolean isActive;
}
