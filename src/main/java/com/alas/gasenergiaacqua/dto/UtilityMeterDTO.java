package com.alas.gasenergiaacqua.dto;

import com.alas.gasenergiaacqua.entity.Reading;
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
public class UtilityMeterDTO {
    private UUID uuid;
    private UUID user;
    private UUID address;
    private String resourceType;
    private String inputMethod;
    private String servicePointIdentifier;
    private String serialNumber;
    private String meterName;
    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<UUID> readings;
}