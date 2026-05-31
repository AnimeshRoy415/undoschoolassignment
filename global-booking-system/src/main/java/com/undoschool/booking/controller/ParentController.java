package com.undoschool.booking.controller;

import com.undoschool.booking.dto.request.BookingRequest;
import com.undoschool.booking.dto.request.ParentRequestDTO;
import com.undoschool.booking.dto.response.BookingResponse;
import com.undoschool.booking.dto.response.OfferingResponse;
import com.undoschool.booking.dto.response.ParentBookingResponse;
import com.undoschool.booking.entity.Parent;
import com.undoschool.booking.service.BookingService;
import com.undoschool.booking.service.ParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;
    private final BookingService bookingService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Parent createParent(@Valid @RequestBody ParentRequestDTO request) {
        return parentService.createParent(request);
    }

    @GetMapping("/parents/{parentId}/offerings")
    public List<OfferingResponse> getAvailableOfferings(
            @PathVariable Long parentId
    ) {

        return parentService.getAvailableOfferings(
                parentId
        );
    }

    @PostMapping("/parents/{parentId}/bookings")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse bookOffering(
            @PathVariable Long parentId,
            @Valid @RequestBody BookingRequest request
    ) {

        return bookingService.bookOffering(
                parentId,
                request
        );
    }

    @GetMapping("/parents/{parentId}/bookings")
    public List<ParentBookingResponse> getBookings(
            @PathVariable Long parentId
    ) {

        return parentService.getBookings(
                parentId
        );
    }
}