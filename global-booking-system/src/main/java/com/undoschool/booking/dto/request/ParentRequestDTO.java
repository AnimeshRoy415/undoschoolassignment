package com.undoschool.booking.dto.request;

import lombok.Data;

@Data
public class ParentRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String timezone;   // required (IANA format)
    private String country;
    private String phoneNumber;
}