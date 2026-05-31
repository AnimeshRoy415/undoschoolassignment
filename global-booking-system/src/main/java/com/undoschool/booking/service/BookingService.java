package com.undoschool.booking.service;

import com.undoschool.booking.dto.request.BookingRequestDTO;
import com.undoschool.booking.dto.response.BookingResponseDTO;

import java.util.List;

public interface BookingService {

    BookingResponseDTO bookOffering(
            Long parentId,
            BookingRequestDTO request
    );

    List<BookingResponseDTO> getBookingsByParent(Long parentId);

    List<BookingResponseDTO> getAllBookings();
}