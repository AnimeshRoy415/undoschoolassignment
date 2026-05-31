package com.undoschool.booking.dto;

import lombok.Data;

@Data
public class TeacherRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String timezone; // e.g. "Asia/Kolkata"
}