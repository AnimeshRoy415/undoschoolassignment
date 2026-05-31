package com.undoschool.booking.service.impl;

import com.undoschool.booking.dto.request.CourseRequestDTO;
import com.undoschool.booking.dto.response.CourseResponseDTO;
import com.undoschool.booking.entity.Course;
import com.undoschool.booking.repository.CourseRepository;
import com.undoschool.booking.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public CourseResponseDTO createCourse(CourseRequestDTO request) {

        Course course = Course.builder()
                .courseName(request.getCourseName())
                .description(request.getDescription())
                .durationInWeeks(request.getDurationInWeeks())
                .build();

        Course saved = courseRepository.save(course);

        return mapToDTO(saved);
    }

    @Override
    public CourseResponseDTO getCourseById(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

        return mapToDTO(course);
    }

    @Override
    public List<CourseResponseDTO> getAllCourses() {

        return courseRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO request) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

        course.setCourseName(request.getCourseName());
        course.setDescription(request.getDescription());
        course.setDurationInWeeks(request.getDurationInWeeks());

        Course updated = courseRepository.save(course);

        return mapToDTO(updated);
    }

    @Override
    public void deleteCourse(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

        courseRepository.delete(course);
    }

    private CourseResponseDTO mapToDTO(Course course) {
        return CourseResponseDTO.builder()
                .courseId(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .durationInWeeks(course.getDurationInWeeks())
                .build();
    }
}