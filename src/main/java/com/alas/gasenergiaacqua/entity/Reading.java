package com.alas.gasenergiaacqua.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "readings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"meter_id", "reading_timestamp"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "meter_id", nullable = false)
    private UtilityMeter meter;

    @Column(name = "value_recorded", nullable = false, precision = 12, scale = 3)
    private BigDecimal valueRecorded;

    @Column(name = "reading_timestamp", nullable = false)
    private LocalDateTime readingTimestamp;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}