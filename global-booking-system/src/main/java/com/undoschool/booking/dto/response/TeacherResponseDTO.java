package com.undoschool.booking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String timezone;
}