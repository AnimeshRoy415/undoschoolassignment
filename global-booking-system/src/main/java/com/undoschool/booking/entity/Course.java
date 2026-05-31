package com.undoschool.booking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course extends BaseEntity {

    @Column(nullable = false)
    private String courseName;

    @Column(length = 2000)
    private String description;

    private Integer durationInWeeks;
}