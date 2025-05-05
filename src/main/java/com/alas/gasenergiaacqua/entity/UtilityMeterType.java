package com.alas.gasenergiaacqua.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "utility_meter_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilityMeterType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "method_name", nullable = false, unique = true)
    private String methodName;
}