package com.alas.gasenergiaacqua.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadingDTO {
    private Integer id;
    private Integer meterId;
    private BigDecimal valueRecorded;
    private LocalDateTime readingTimestamp;
    private String notes;
}