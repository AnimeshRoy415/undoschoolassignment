package com.undoschool.booking.entity;

import com.undoschool.booking.enums.OfferingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "offerings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offering extends BaseEntity {

    @Column(nullable = false)
    private String offeringName;

    private String batchType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OfferingStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    // snapshot timezone (IMPORTANT for consistency)
    @Column(nullable = false)
    private String teacherTimezone;

    @OneToMany(
            mappedBy = "offering",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("startTimeUtc ASC")
    private List<Session> sessions = new ArrayList<>();
}