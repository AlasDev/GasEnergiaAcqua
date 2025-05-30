package com.alas.gasenergiaacqua.filter;

import com.alas.gasenergiaacqua.entity.User;
import com.alas.gasenergiaacqua.entity.UserType;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

/**
 * Filter params to only return the values that satisfy the criteria.
 * If a filter param is empty, it will not be considered
 */
@Data
public class UserFilter {
    String name;
    String surname;
    String email;
    UserType userType;
    LocalDateTime fromCreatedAt;
    LocalDateTime toCreatedAt;

    public Specification<User> toSpecification() {
        return Specification.<User>where(null)
                .and(likeNameSpecification(name))
                .and(likeSurnameSpecification(surname))
                .and(likeEmailSpecification(email))
                .and(equalUserTypeSpecification(userType))
                .and(greaterThanOrEqualToCreatedAtSpecification(fromCreatedAt))
                .and(lessThanCreatedAtSpecification(toCreatedAt));
    }

    //name
    private Specification<User> likeNameSpecification(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
    }

    //surname
    private Specification<User> likeSurnameSpecification(String surname) {
        return (root, query, criteriaBuilder) -> {
            if (surname == null || surname.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(root.get("surname"), "%" + surname + "%");
        };
    }

    //email
    private Specification<User> likeEmailSpecification(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null || email.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(root.get("email"), "%" + email + "%");
        };
    }

    //userType
    private Specification<User> equalUserTypeSpecification(UserType userType) {
        return (root, query, criteriaBuilder) -> {
            if (userType == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("userType"), userType);
        };
    }

    //from createdAt
    private Specification<User> greaterThanOrEqualToCreatedAtSpecification(LocalDateTime localDateTime) {
        return (root, query, criteriaBuilder) -> {
            if (localDateTime == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), localDateTime);
        };
    }

    //to createdAt
    private Specification<User> lessThanCreatedAtSpecification(LocalDateTime localDateTime) {
        return (root, query, criteriaBuilder) -> {
            if (localDateTime == null) {
                return null;
            }
            return criteriaBuilder.lessThan(root.get("createdAt"), localDateTime);
        };
    }
}