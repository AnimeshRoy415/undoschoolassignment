package com.undoschool.booking.service;

import com.undoschool.booking.dto.request.TeacherRequestDto;
import com.undoschool.booking.entity.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher addTeacher(TeacherRequestDto dto);

    List<Teacher> getAllTeachers();

    Teacher getTeacherById(Long id);

    Teacher updateTeacher(Long id, TeacherRequestDto dto);

    void deleteTeacher(Long id);
}
