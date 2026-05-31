package com.undoschool.booking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZoneId;

@Entity
@Table(name = "parents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parent extends BaseEntity {

    private String firstName;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String timezone;

    private String country;

    @Column(unique = true)
    private String phoneNumber;

    @PrePersist
    @PreUpdate
    public void validateTimezone() {
        ZoneId.of(timezone);
    }
}