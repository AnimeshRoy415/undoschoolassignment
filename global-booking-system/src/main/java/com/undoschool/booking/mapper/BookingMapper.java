package com.undoschool.booking.mapper;

import com.undoschool.booking.dto.response.BookingResponseDTO;
import com.undoschool.booking.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public static BookingResponseDTO toDto(
            Booking booking) {

        return BookingResponseDTO.builder()
                .bookingId(booking.getId())
                .parentId(booking.getParent().getId())
                .offeringId(booking.getOffering().getId())
                .status(booking.getStatus().name())
                .message("Booking successful")
                .build();
    }
}
