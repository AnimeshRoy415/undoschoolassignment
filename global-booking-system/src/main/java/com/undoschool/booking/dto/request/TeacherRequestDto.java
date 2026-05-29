package com.undoschool.booking.dto.request;

import lombok.Data;

@Data
public class TeacherRequestDto {

    private String firstName;

    private String lastName;

    private String email;

    private String timezone;

    private String country;

    private String expertise;
}