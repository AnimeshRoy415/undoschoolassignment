package com.undoschool.booking.service;

import com.undoschool.booking.dto.request.BookingRequest;
import com.undoschool.booking.dto.response.BookingResponse;

public interface BookingService {

    BookingResponse bookOffering(
            Long parentId,
            BookingRequest request
    );
}