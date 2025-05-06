package com.alas.gasenergiaacqua.filter;

import com.alas.gasenergiaacqua.entity.UtilityMeter;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Filter params to only return the values that satisfy the criteria. If a filter param is empty it will not be considered
 */
@Data
public class UtilityMeterFilter {
    private UUID id;
    private UUID userId;
    private UUID addressId;

    private Integer resourceTypeId;
    private Integer utilityMeterTypeId;

    private String servicePointIdentifier;
    private String serialNumber;
    private String meterName;
    private Boolean isActive;

    private LocalDateTime fromCreatedAt;
    private LocalDateTime toCreatedAt;
    private LocalDateTime fromUpdatedAt;
    private LocalDateTime toUpdatedAt;

    public Specification<UtilityMeter> toSpecification() {
        return Specification.<UtilityMeter>where(null)
                .and(equalUuidSpecification(id))
                .and(greaterThanOrEqualToUpdatedAtSpecification(fromUpdatedAt))
                .and(lessThanUpdatedAtSpecification(toUpdatedAt))
                .and(greaterThanOrEqualToCreatedAtSpecification(fromCreatedAt))
                .and(lessThanCreatedAtSpecification(toCreatedAt));
    }

    //uuid
    private Specification<UtilityMeter> equalUuidSpecification(UUID id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("uuid"), id.toString());
        };
    }

    //from updatedAt
    private Specification<UtilityMeter> greaterThanOrEqualToUpdatedAtSpecification(LocalDateTime localDateTime) {
        return (root, query, criteriaBuilder) -> {
            if (localDateTime == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("updatedAt"), localDateTime);
        };
    }

    //to updatedAt
    private Specification<UtilityMeter> lessThanUpdatedAtSpecification(LocalDateTime localDateTime) {
        return (root, query, criteriaBuilder) -> {
            if (localDateTime == null) {
                return null;
            }
            return criteriaBuilder.lessThan(root.get("updatedAt"), localDateTime);
        };
    }

    //from createdAt
    private Specification<UtilityMeter> greaterThanOrEqualToCreatedAtSpecification(LocalDateTime localDateTime) {
        return (root, query, criteriaBuilder) -> {
            if (localDateTime == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), localDateTime);
        };
    }

    //to createdAt
    private Specification<UtilityMeter> lessThanCreatedAtSpecification(LocalDateTime localDateTime) {
        return (root, query, criteriaBuilder) -> {
            if (localDateTime == null) {
                return null;
            }
            return criteriaBuilder.lessThan(root.get("createdAt"), localDateTime);
        };
    }
}
