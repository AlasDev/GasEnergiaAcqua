package com.alas.gasenergiaacqua.filter;

import com.alas.gasenergiaacqua.entity.Address;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Filter params to only return the values that satisfy the criteria. If a filter param is empty it will not be considered
 */
@Data
public class AddressFilter {
    UUID id;
    String streetAddress;
    String city;
    String postalCode;
    String country;
    LocalDateTime fromCreatedAt;
    LocalDateTime toCreatedAt;

    public Specification<Address> toSpecification() {
        return Specification.<Address>where(null)
                .and(equalUuidSpecification(id))
                .and(likeStreetAddressSpecification(streetAddress))
                .and(likeCitySpecification(city))
                .and(likePostalCodeSpecification(postalCode))
                .and(likeCountrySpecification(country))
                .and(greaterThanOrEqualToCreatedAtSpecification(fromCreatedAt))
                .and(lessThanCreatedAtSpecification(toCreatedAt));
    }

    //uuid
    private Specification<Address> equalUuidSpecification(UUID id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("uuid"), id.toString());
        };
    }

    //streetAddress
    private Specification<Address> likeStreetAddressSpecification(String streetAddress) {
        return (root, query, criteriaBuilder) -> {
            if (streetAddress == null || streetAddress.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(root.get("streetAddress"), "%" + streetAddress + "%");
        };
    }

    //city
    private Specification<Address> likeCitySpecification(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null || city.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(root.get("city"), "%" + city + "%");
        };
    }

    //streetAddress
    private Specification<Address> likePostalCodeSpecification(String postalCode) {
        return (root, query, criteriaBuilder) -> {
            if (postalCode == null || postalCode.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(root.get("postalCode"), "%" + postalCode + "%");
        };
    }

    //streetAddress
    private Specification<Address> likeCountrySpecification(String country) {
        return (root, query, criteriaBuilder) -> {
            if (country == null || country.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(root.get("country"), "%" + country + "%");
        };
    }

    //from createdAt
    private Specification<Address> greaterThanOrEqualToCreatedAtSpecification(LocalDateTime localDateTime) {
        return (root, query, criteriaBuilder) -> {
            if (localDateTime == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), localDateTime);
        };
    }

    //to createdAt
    private Specification<Address> lessThanCreatedAtSpecification(LocalDateTime localDateTime) {
        return (root, query, criteriaBuilder) -> {
            if (localDateTime == null) {
                return null;
            }
            return criteriaBuilder.lessThan(root.get("createdAt"), localDateTime);
        };
    }
}
