package com.alas.gasenergiaacqua.filter;

import com.alas.gasenergiaacqua.entity.UtilityMeter;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Filter params to only return the values that satisfy the criteria.
 * If a filter param is empty, it will not be considered
 */
@Data
public class UtilityMeterFilter {
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
    private LocalDateTime fromInstallationDateAt;
    private LocalDateTime toInstallationDateAt;
    private LocalDateTime fromUpdatedAt;
    private LocalDateTime toUpdatedAt;

    public Specification<UtilityMeter> toSpecification() {
        return Specification.<UtilityMeter>where(null)
                .and(equalUserIdSpecification(userId))
                .and(equalAddressIdSpecification(addressId))

                .and(equalResourceTypeIdSpecification(resourceTypeId))
                .and(equalUtilityMeterTypeIdSpecification(utilityMeterTypeId))

                .and(likeServicePointIdentifierSpecification(servicePointIdentifier))
                .and(likeSerialNumberSpecification(serialNumber))
                .and(likeMeterNameSpecification(meterName))
                .and(equalIsActiveSpecification(isActive))

                .and(greaterThanOrEqualToCreatedAtSpecification(fromCreatedAt))
                .and(lessThanCreatedAtSpecification(toCreatedAt))
                .and(greaterThanOrEqualToinstallationDateAtSpecification(fromInstallationDateAt))
                .and(lessThantoInstallationDateAtSpecification(toInstallationDateAt))
                .and(greaterThanOrEqualToUpdatedAtSpecification(fromUpdatedAt))
                .and(lessThanUpdatedAtSpecification(toUpdatedAt));
    }

    //user uuid
    private Specification<UtilityMeter> equalUserIdSpecification(UUID id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("userId"), id);
        };
    }

    //address uuid
    private Specification<UtilityMeter> equalAddressIdSpecification(UUID id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("addressId"), id);
        };
    }

    //resource type id
    private Specification<UtilityMeter> equalResourceTypeIdSpecification(Integer id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("resourceTypeId"), id);
        };
    }

    //utility meter type id
    private Specification<UtilityMeter> equalUtilityMeterTypeIdSpecification(Integer id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("utilityMeterTypeId"), id);
        };
    }

    //service point identifier
    private Specification<UtilityMeter> likeServicePointIdentifierSpecification(String string) {
        return (root, query, criteriaBuilder) -> {
            if (string == null || string.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(root.get("servicePointIdentifier"), string);
        };
    }

    //serial number
    private Specification<UtilityMeter> likeSerialNumberSpecification(String string) {
        return (root, query, criteriaBuilder) -> {
            if (string == null || string.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(root.get("serialNumber"), string);
        };
    }

    //meter name
    private Specification<UtilityMeter> likeMeterNameSpecification(String string) {
        return (root, query, criteriaBuilder) -> {
            if (string == null || string.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(root.get("meterName"), string);
        };
    }

    //status
    private Specification<UtilityMeter> equalIsActiveSpecification(Boolean bool) {
        return (root, query, criteriaBuilder) -> {
            if (bool == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("isActive"), bool);
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

    //from installationDateAt
    private Specification<UtilityMeter> greaterThanOrEqualToinstallationDateAtSpecification(LocalDateTime localDateTime) {
        return (root, query, criteriaBuilder) -> {
            if (localDateTime == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("installationDate"), localDateTime);
        };
    }

    //to toInstallationDateAt
    private Specification<UtilityMeter> lessThantoInstallationDateAtSpecification(LocalDateTime localDateTime) {
        return (root, query, criteriaBuilder) -> {
            if (localDateTime == null) {
                return null;
            }
            return criteriaBuilder.lessThan(root.get("installationDate"), localDateTime);
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
