package com.alas.gasenergiaacqua.repository;

import com.alas.gasenergiaacqua.entity.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceTypeRepository extends JpaRepository<ResourceType, Integer>, JpaSpecificationExecutor<ResourceType> {
}
