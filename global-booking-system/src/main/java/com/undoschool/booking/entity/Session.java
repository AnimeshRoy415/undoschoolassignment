package com.undoschool.booking.entity;

import com.undoschool.booking.enums.SessionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "sessions",
        indexes = {
                @Index(name = "idx_session_offering", columnList = "offering_id"),
                @Index(name = "idx_session_time", columnList = "start_time_utc, end_time_utc"),
                @Index(name = "idx_session_offering_time", columnList = "offering_id, start_time_utc")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "offering_id", nullable = false)
    private Offering offering;

    @Column(nullable = false)
    private Instant startTimeUtc;

    @Column(nullable = false)
    private Instant endTimeUtc;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionStatus status;

    @PrePersist
    @PreUpdate
    public void validateTime() {
        if (endTimeUtc.isBefore(startTimeUtc) || endTimeUtc.equals(startTimeUtc)) {
            throw new IllegalArgumentException("Invalid session time range");
        }
    }
}