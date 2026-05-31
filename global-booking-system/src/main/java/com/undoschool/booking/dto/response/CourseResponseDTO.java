package com.undoschool.booking.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponseDTO {

    private Long courseId;
    private String courseName;
    private String description;
    private Integer durationInWeeks;
}