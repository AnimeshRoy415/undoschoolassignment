package com.undoschool.booking.service;

import com.undoschool.booking.dto.request.CourseRequestDTO;
import com.undoschool.booking.dto.request.UpdateCourseRequest;
import com.undoschool.booking.dto.response.CourseResponseDTO;

import java.util.List;

public interface CourseService {

    CourseResponseDTO createCourse(CourseRequestDTO request);

    CourseResponseDTO getCourseById(Long id);

    List<CourseResponseDTO> getAllCourses();

    CourseResponseDTO updateCourse(Long id, CourseRequestDTO request);

    void deleteCourse(Long id);
}
