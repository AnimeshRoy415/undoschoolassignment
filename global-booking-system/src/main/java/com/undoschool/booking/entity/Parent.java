package com.undoschool.booking.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parents")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Parent extends BaseEntity {

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String timezone;

    private String country;

    private String phoneNumber;
}