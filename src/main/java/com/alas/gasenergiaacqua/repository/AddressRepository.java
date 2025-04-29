package com.alas.gasenergiaacqua.repository;

import com.alas.gasenergiaacqua.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AddressRepository extends JpaRepository<Address, String>, JpaSpecificationExecutor<Address> {
}
