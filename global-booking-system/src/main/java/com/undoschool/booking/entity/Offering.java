package com.undoschool.booking.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Offering extends BaseEntity {

    private String title;

    private Integer maxStudents;

    private Integer enrolledStudents = 0;

    private Boolean active = true;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Course course;
}