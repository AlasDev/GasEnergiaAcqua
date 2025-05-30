package com.alas.gasenergiaacqua.repository;

import com.alas.gasenergiaacqua.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
}
