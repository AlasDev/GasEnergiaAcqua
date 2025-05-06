package com.alas.gasenergiaacqua.repository;

import com.alas.gasenergiaacqua.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    /**
     * @param email email
     * @return 'true' if a user with that email already exists
     */
    Boolean existsByEmail(String email);

    /**
     * @param email email of user
     * @param password hashed password of user
     * @return user
     */
    Optional<User> findByEmailAndPassword(String email, String password);

    /**
     * @param email email of user
     * @return user
     */
    Optional<User> findByEmail(String email);
}
