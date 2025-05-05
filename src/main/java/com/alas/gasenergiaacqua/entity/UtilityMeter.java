package com.alas.gasenergiaacqua.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "utility_meters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilityMeter {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_type_id", nullable = false)
    private ResourceType resourceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utility_meter_type_id", nullable = false)
    private UtilityMeterType utilityMeterType;

    @Column(name = "service_point_identifier", unique = true)
    private String servicePointIdentifier;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "meter_name")
    private String meterName;

    @Column(name = "installation_date")
    private LocalDate installationDate;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "utilityMeter", cascade = CascadeType.ALL)
    private List<Reading> readings;
}