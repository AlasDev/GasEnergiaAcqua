package com.alas.gasenergiaacqua.repository;

import com.alas.gasenergiaacqua.entity.UtilityMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UtilityMeterRepository extends JpaRepository<UtilityMeter, UUID>, JpaSpecificationExecutor<UtilityMeter> {
}
