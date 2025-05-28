package com.alas.gasenergiaacqua.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilityMeterSummaryDTO {
    private UUID id;
    private Integer resourceTypeId;
    private String serialNumber;
    private String meterName;
    private Boolean isActive;
    private LocalDateTime updatedAt;
}
