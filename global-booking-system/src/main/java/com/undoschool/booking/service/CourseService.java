package com.undoschool.booking.service;

import com.undoschool.booking.dto.request.CourseRequestDto;
import com.undoschool.booking.entity.Course;

import java.util.List;

public interface CourseService {

    Course addCourse(CourseRequestDto dto);

    List<Course> getAllCourses();

    Course getCourseById(Long id);

    Course updateCourse(Long id, CourseRequestDto dto);

    void deleteCourse(Long id);
}