package com.undoschool.booking.mapper;

import com.undoschool.booking.dto.request.CourseRequestDto;
import com.undoschool.booking.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public Course toEntity(CourseRequestDto dto) {
        if (dto == null) return null;

        Course course = new Course();

        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setCategory(dto.getCategory());
        course.setDurationWeeks(dto.getDurationWeeks());
        course.setPrice(dto.getPrice());
        course.setLevel(dto.getLevel());

        return course;
    }
}