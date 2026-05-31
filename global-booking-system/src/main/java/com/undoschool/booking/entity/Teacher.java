package com.undoschool.booking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZoneId;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher extends BaseEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String timezone; // IANA timezone (Asia/Kolkata)

    @PrePersist
    @PreUpdate
    public void validateTimezone() {
        ZoneId.of(timezone); // ensures valid timezone
    }
}