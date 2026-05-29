package com.undoschool.booking.dto.request;

import com.undoschool.booking.entity.CourseLevel;
import lombok.Data;

@Data
public class CourseRequestDto {

    private String title;

    private String description;

    private String category;

    private Integer durationWeeks;

    private Double price;

    private CourseLevel level;
}
