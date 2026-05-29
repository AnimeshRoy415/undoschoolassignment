package com.undoschool.booking.service.Impl;

import com.undoschool.booking.dto.request.CourseRequestDto;
import com.undoschool.booking.entity.Course;
import com.undoschool.booking.mapper.CourseMapper;
import com.undoschool.booking.repository.CourseRepository;
import com.undoschool.booking.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public Course addCourse(CourseRequestDto dto) {
        Course course = courseMapper.toEntity(dto);
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    @Override
    public Course updateCourse(Long id, CourseRequestDto dto) {

        Course existing = getCourseById(id);

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setCategory(dto.getCategory());
        existing.setDurationWeeks(dto.getDurationWeeks());
        existing.setPrice(dto.getPrice());
        existing.setLevel(dto.getLevel());

        return courseRepository.save(existing);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
    }
}