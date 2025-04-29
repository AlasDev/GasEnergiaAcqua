package com.alas.gasenergiaacqua.repository;

import com.alas.gasenergiaacqua.entity.UtilityMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilityMeterRepository extends JpaRepository<UtilityMeter, String>, JpaSpecificationExecutor<UtilityMeter> {
}
