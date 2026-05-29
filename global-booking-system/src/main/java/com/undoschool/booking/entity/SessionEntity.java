package com.undoschool.booking.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(
        name = "sessions",
        indexes = {
                @Index(
                        name = "idx_start_time",
                        columnList = "startTimeUtc"
                )
        }
)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SessionEntity extends BaseEntity {

    @ManyToOne
    private Offering offering;

    private Instant startTimeUtc;

    private Instant endTimeUtc;

    private String meetingLink;

    private String sessionTitle;

    private Boolean cancelled = false;
}