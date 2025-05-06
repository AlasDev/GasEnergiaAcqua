package com.alas.gasenergiaacqua.filter;

import com.alas.gasenergiaacqua.entity.Reading;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ReadingFilter {
    UUID id;
    BigDecimal valueRecorded;
    LocalDateTime fromReadingTimestamp;
    LocalDateTime toReadingTimestamp;
    String notes;
    LocalDateTime fromCreatedAt;
    LocalDateTime toCreatedAt;

    public Specification<Reading> toSpecification() {
        return Specification.<Reading>where(null)
                .and(equalUuidSpecification(id))
                .and(equalValueRecordedSpecification(valueRecorded))
                .and(greaterThanOrEqualToReadingTimestampSpecification(fromReadingTimestamp))
                .and(lessThanReadingTimestampSpecification(toReadingTimestamp))
                .and(likeNotesSpecification(notes))
                .and(greaterThanOrEqualToCreatedAtSpecification(fromCreatedAt))
                .and(lessThanCreatedAtSpecification(toCreatedAt));
    }

    //uuid
    private Specification<Reading> equalUuidSpecification(UUID id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("uuid"), id.toString());
        };
    }

    //valueRecorded
    private Specification<Reading> equalValueRecordedSpecification(BigDecimal valueRecorded) {
        return (root, query, criteriaBuilder) -> {
            if (valueRecorded == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("valueRecorded"), valueRecorded);
        };
    }

    //from readingTimestamp
    private Specification<Reading> greaterThanOrEqualToReadingTimestampSpecification(LocalDateTime localDateTime) {
        return (root, query, criteriaBuilder) -> {
            if (localDateTime == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("readingTimestamp"), localDateTime);
        };
    }

    //to readingTimestamp
    private Specification<Reading> lessThanReadingTimestampSpecification(LocalDateTime localDateTime) {
        return (root, query, criteriaBuilder) -> {
            if (localDateTime == null) {
                return null;
            }
            return criteriaBuilder.lessThan(root.get("readingTimestamp"), localDateTime);
        };
    }

    //notes
    private Specification<Reading> likeNotesSpecification(String notes) {
        return (root, query, criteriaBuilder) -> {
            if (notes == null || notes.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(root.get("notes"), "%" + notes + "%");
        };
    }

    //from createdAt
    private Specification<Reading> greaterThanOrEqualToCreatedAtSpecification(LocalDateTime localDateTime) {
        return (root, query, criteriaBuilder) -> {
            if (localDateTime == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), localDateTime);
        };
    }

    //to createdAt
    private Specification<Reading> lessThanCreatedAtSpecification(LocalDateTime localDateTime) {
        return (root, query, criteriaBuilder) -> {
            if (localDateTime == null) {
                return null;
            }
            return criteriaBuilder.lessThan(root.get("createdAt"), localDateTime);
        };
    }
}
