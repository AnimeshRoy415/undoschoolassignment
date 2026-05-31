package com.undoschool.booking.exception;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private String message;
    private String errorCode;
    private int status;
    private Instant timestamp;
}