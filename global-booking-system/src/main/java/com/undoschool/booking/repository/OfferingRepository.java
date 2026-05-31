package com.undoschool.booking.repository;

import com.undoschool.booking.entity.Offering;
import com.undoschool.booking.enums.OfferingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferingRepository
        extends JpaRepository<Offering, Long> {

    @Query("""
           SELECT DISTINCT o
           FROM Offering o
           JOIN FETCH o.course
           JOIN FETCH o.teacher
           WHERE o.teacher.id = :teacherId
           """)
    List<Offering> findTeacherOfferings(
            @Param("teacherId") Long teacherId
    );

    @Query("""
           SELECT DISTINCT o
           FROM Offering o
           JOIN FETCH o.course
           JOIN FETCH o.teacher
           WHERE o.status = :status
           """)
    List<Offering> findActiveOfferings(
            @Param("status") OfferingStatus status
    );
}