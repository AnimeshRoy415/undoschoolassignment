package com.undoschool.booking.mapper;

import com.undoschool.booking.dto.response.CourseResponseDTO;
import com.undoschool.booking.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public static CourseResponseDTO toDto(Course course) {

        return CourseResponseDTO.builder()
                .courseId(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .durationInWeeks(course.getDurationInWeeks())
                .build();
    }
}