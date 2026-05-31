package com.undoschool.booking.service;

import com.undoschool.booking.dto.request.CourseRequestDTO;
import com.undoschool.booking.dto.request.UpdateCourseRequest;
import com.undoschool.booking.dto.response.CourseResponseDTO;

import java.util.List;

public interface CourseService {

    CourseResponseDTO createCourse(CourseRequestDTO request);

    CourseResponseDTO getCourseById(Long courseId);

    List<CourseResponseDTO> getAllCourses();

    CourseResponseDTO updateCourse(Long courseId, UpdateCourseRequest request);

    void deleteCourse(Long courseId);
}
