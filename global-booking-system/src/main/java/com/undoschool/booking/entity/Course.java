package com.undoschool.booking.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Course extends BaseEntity {

    private String title;

    @Column(length = 1000)
    private String description;

    private String category;

    private Integer durationWeeks;

    private Double price;

    @Enumerated(EnumType.STRING)
    private CourseLevel level;
}