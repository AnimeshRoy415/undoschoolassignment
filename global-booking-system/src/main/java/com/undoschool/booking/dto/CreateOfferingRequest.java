package com.undoschool.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOfferingRequest {

    @NotNull
    private Long teacherId;

    @NotNull
    private Long courseId;

    @NotBlank
    private String title;
}