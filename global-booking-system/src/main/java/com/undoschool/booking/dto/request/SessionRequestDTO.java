package com.undoschool.booking.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessionRequestDTO {
    private LocalDateTime startTime;  // teacher local time
    private LocalDateTime endTime;     // teacher local time
}