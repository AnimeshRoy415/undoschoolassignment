package com.undoschool.booking.service;

import com.undoschool.booking.dto.TeacherRequestDTO;
import com.undoschool.booking.dto.TeacherResponseDTO;

import java.util.List;

public interface TeacherService {

    TeacherResponseDTO createTeacher(TeacherRequestDTO request);

    TeacherResponseDTO getTeacherById(Long id);

    List<TeacherResponseDTO> getAllTeachers();

    TeacherResponseDTO updateTeacher(Long id, TeacherRequestDTO request);

    void deleteTeacher(Long id);
}