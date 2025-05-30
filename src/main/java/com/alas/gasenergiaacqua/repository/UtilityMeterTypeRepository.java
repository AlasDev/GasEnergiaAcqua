package com.alas.gasenergiaacqua.repository;

import com.alas.gasenergiaacqua.entity.UtilityMeterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilityMeterTypeRepository extends JpaRepository<UtilityMeterType, Integer>{
}
