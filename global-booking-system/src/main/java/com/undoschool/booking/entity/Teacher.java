package com.undoschool.booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Teacher extends BaseEntity {

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String timezone;

    private String country;

    private String expertise;
}