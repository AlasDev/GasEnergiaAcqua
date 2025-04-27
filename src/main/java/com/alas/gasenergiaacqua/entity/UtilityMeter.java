package com.alas.gasenergiaacqua.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "utility_meters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilityMeter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type", nullable = false)
    private ResourceType resourceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "input_method", nullable = false)
    private InputMethod inputMethod = InputMethod.MANUAL;

    @Column(name = "service_point_identifier", unique = true)
    private String servicePointIdentifier;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "meter_name")
    private String meterName;

    @Column(name = "installation_date")
    private LocalDate installationDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "meter", cascade = CascadeType.ALL)
    private List<Reading> readings;

    public enum ResourceType {
        WATER, GAS, ELECTRICITY
    }

    public enum InputMethod {
        MANUAL, AUTOMATIC
    }
}