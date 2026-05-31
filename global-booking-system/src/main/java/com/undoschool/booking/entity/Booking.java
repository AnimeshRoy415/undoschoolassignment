package com.undoschool.booking.entity;

import com.undoschool.booking.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "bookings",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_parent_offering",
                        columnNames = {"parent_id", "offering_id"}
                ),
                @UniqueConstraint(
                        name = "uk_booking_reference",
                        columnNames = {"booking_reference"}
                )
        },
        indexes = {
                @Index(name = "idx_booking_parent", columnList = "parent_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "offering_id", nullable = false)
    private Offering offering;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(nullable = false)
    private Instant bookedAt;

    // VERY IMPORTANT for concurrency safety + idempotency
    @Column(name = "booking_reference", nullable = false, unique = true)
    private String bookingReference;

    @PrePersist
    public void prePersist() {
        if (bookedAt == null) {
            bookedAt = Instant.now();
        }
        if (bookingReference == null) {
            bookingReference = java.util.UUID.randomUUID().toString();
        }
    }
}