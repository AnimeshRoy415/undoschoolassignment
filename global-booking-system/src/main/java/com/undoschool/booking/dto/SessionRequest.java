package com.undoschool.booking.dto;

import lombok.Data;

@Data
public class SessionRequest {

    private String startTime;

    private String endTime;

    private String timezone;
}