package com.undoschool.booking.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCourseRequest {

    private String courseName;
    private String description;
    private Integer durationInWeeks;
}