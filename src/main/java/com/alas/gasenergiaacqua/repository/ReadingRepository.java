package com.alas.gasenergiaacqua.repository;

import com.alas.gasenergiaacqua.entity.Reading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, String>, JpaSpecificationExecutor<Reading> {
}
