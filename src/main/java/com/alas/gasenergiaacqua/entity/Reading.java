package com.alas.gasenergiaacqua.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "readings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_id", nullable = false)
    private UtilityMeter utilityMeter;

    @Column(name = "value_recorded", nullable = false, precision = 12, scale = 3)
    private BigDecimal valueRecorded;

    @Column(name = "reading_timestamp", nullable = false)
    private LocalDateTime readingTimestamp;

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;
}