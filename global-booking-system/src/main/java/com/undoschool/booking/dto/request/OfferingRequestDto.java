package com.undoschool.booking.dto.request;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OfferingRequestDto {

    @NotBlank
    private String title;

    @NotNull
    @Min(1)
    private Integer maxStudents;

    private Integer enrolledStudents = 0;

    private Boolean active = true;

    @NotNull
    private Long teacherId;

    @NotNull
    private Long courseId;
}