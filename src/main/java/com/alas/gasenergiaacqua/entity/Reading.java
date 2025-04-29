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
@Table(name = "readings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"meter_id", "reading_timestamp"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "meter_uuid", nullable = false)
    private UtilityMeter meter;

    @Column(name = "value_recorded", nullable = false, precision = 12, scale = 3)
    private BigDecimal valueRecorded;

    @Column(name = "reading_timestamp", nullable = false)
    private LocalDateTime readingTimestamp;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Immutable
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}