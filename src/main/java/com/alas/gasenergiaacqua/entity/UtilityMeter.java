package com.alas.gasenergiaacqua.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

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
    @Column(name = "uuid")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_uuid", nullable = false)
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type", nullable = false)
    private ResourceType resourceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "input_method", nullable = false)
    private InputMethod inputMethod;

    @Column(name = "service_point_identifier", unique = true)
    private String servicePointIdentifier;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "meter_name")
    private String meterName;

    @Column(name = "installation_date")
    private LocalDate installationDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Immutable
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Immutable
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "meter", cascade = CascadeType.ALL)
    private List<Reading> readings;

    public enum ResourceType {
        WATER, GAS, ELECTRICITY
    }

    public enum InputMethod {
        MANUAL, AUTOMATIC
    }
}