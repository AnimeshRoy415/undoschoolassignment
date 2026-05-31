package com.undoschool.booking.controller;

import com.undoschool.booking.dto.common.ApiResponse;
import com.undoschool.booking.dto.request.BookingRequestDTO;
import com.undoschool.booking.dto.response.BookingResponseDTO;
import com.undoschool.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // ---------------- CREATE BOOKING ----------------
    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponseDTO>> bookOffering(
            @RequestParam Long parentId,
            @RequestBody BookingRequestDTO request) {

        BookingResponseDTO response = bookingService.bookOffering(parentId, request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Booking created successfully", response)
        );
    }

    // ---------------- GET ALL BOOKINGS ----------------
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookingResponseDTO>>> getAllBookings() {

        List<BookingResponseDTO> response = bookingService.getAllBookings();

        return ResponseEntity.ok(
                new ApiResponse<>(true, "All bookings fetched", response)
        );
    }

    // ---------------- GET BOOKINGS BY PARENT ----------------
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<ApiResponse<List<BookingResponseDTO>>> getBookingsByParent(
            @PathVariable Long parentId) {

        List<BookingResponseDTO> response =
                bookingService.getBookingsByParent(parentId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Parent bookings fetched", response)
        );
    }
}