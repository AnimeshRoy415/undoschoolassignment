package com.undoschool.booking.dto;

import lombok.Data;

@Data
public class BookingRequest {

    private Long parentId;

    private Long offeringId;
}