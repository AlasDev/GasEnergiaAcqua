package com.alas.gasenergiaacqua.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadingDTO {
    private UUID id;
    private UUID utilityMeterId;
    private BigDecimal valueRecorded;
    private LocalDateTime readingTimestamp;
    private String notes;

    private LocalDateTime createdAt;
}