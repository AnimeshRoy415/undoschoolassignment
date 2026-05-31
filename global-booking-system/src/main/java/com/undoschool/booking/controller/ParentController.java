package com.undoschool.booking.controller;

import com.undoschool.booking.dto.common.ApiResponse;
import com.undoschool.booking.dto.request.ParentRequestDTO;
import com.undoschool.booking.dto.response.ParentResponseDTO;
import com.undoschool.booking.dto.response.BookingResponseDTO;
import com.undoschool.booking.service.BookingService;
import com.undoschool.booking.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;
    private final BookingService bookingService;

    // ---------------- CREATE PARENT ----------------
    @PostMapping
    public ResponseEntity<ApiResponse<ParentResponseDTO>> createParent(
            @RequestBody ParentRequestDTO request) {

        ParentResponseDTO response = parentService.createParent(request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Parent created successfully", response)
        );
    }

    // ---------------- GET BY ID ----------------
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ParentResponseDTO>> getParent(
            @PathVariable Long id) {

        ParentResponseDTO response = parentService.getParentById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Parent fetched successfully", response)
        );
    }

    // ---------------- GET ALL ----------------
    @GetMapping
    public ResponseEntity<ApiResponse<List<ParentResponseDTO>>> getAllParents() {

        List<ParentResponseDTO> response = parentService.getAllParents();

        return ResponseEntity.ok(
                new ApiResponse<>(true, "All parents fetched", response)
        );
    }

    // ---------------- UPDATE ----------------
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ParentResponseDTO>> updateParent(
            @PathVariable Long id,
            @RequestBody ParentRequestDTO request) {

        ParentResponseDTO response = parentService.updateParent(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Parent updated successfully", response)
        );
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteParent(
            @PathVariable Long id) {

        parentService.deleteParent(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Parent deleted successfully", "Deleted parent id: " + id)
        );
    }

    // ---------------- GET BOOKINGS BY PARENT ----------------
    @GetMapping("/{parentId}/bookings")
    public ResponseEntity<ApiResponse<List<BookingResponseDTO>>> getBookingsByParent(
            @PathVariable Long parentId) {

        List<BookingResponseDTO> response =
                bookingService.getBookingsByParent(parentId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Parent bookings fetched", response)
        );
    }
}