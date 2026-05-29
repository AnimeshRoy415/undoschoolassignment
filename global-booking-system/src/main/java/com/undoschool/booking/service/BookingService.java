package com.undoschool.booking.service;

import com.undoschool.booking.dto.BookingRequest;
import com.undoschool.booking.entity.Booking;

import java.util.List;

public interface BookingService {

    Booking bookOffering(BookingRequest request);

    List<Booking> getBookings();
}