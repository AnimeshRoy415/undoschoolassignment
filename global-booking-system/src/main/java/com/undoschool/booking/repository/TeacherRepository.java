package com.undoschool.booking.repository;

import com.undoschool.booking.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {}