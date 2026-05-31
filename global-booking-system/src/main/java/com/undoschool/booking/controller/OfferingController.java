package com.undoschool.booking.controller;

import com.undoschool.booking.dto.common.ApiResponse;
import com.undoschool.booking.dto.request.OfferingRequestDTO;
import com.undoschool.booking.dto.response.OfferingResponseDTO;
import com.undoschool.booking.service.OfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offerings")
@RequiredArgsConstructor
public class OfferingController {

    private final OfferingService offeringService;

    // ---------------- CREATE OFFERING ----------------
    @PostMapping
    public ResponseEntity<ApiResponse<OfferingResponseDTO>> createOffering(
            @RequestBody OfferingRequestDTO request) {

        OfferingResponseDTO response = offeringService.createOffering(request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Offering created successfully", response)
        );
    }

    // ---------------- GET BY ID (TIMEZONE AWARE) ----------------
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OfferingResponseDTO>> getOffering(
            @PathVariable Long id,
            @RequestParam String timeZone) {

        OfferingResponseDTO response =
                offeringService.getOfferingById(id, timeZone);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Offering fetched successfully", response)
        );
    }

    // ---------------- GET ALL (TIMEZONE AWARE) ----------------
    @GetMapping
    public ResponseEntity<ApiResponse<List<OfferingResponseDTO>>> getAllOfferings(
            @RequestParam String timeZone) {

        List<OfferingResponseDTO> response =
                offeringService.getAllOfferings(timeZone);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Offerings fetched successfully", response)
        );
    }

    // ---------------- UPDATE OFFERING ----------------
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OfferingResponseDTO>> updateOffering(
            @PathVariable Long id,
            @RequestBody OfferingRequestDTO request) {

        OfferingResponseDTO response =
                offeringService.updateOffering(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Offering updated successfully", response)
        );
    }

    // ---------------- DELETE OFFERING ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteOffering(
            @PathVariable Long id) {

        offeringService.deleteOffering(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Offering deleted successfully", "Deleted offering id: " + id)
        );
    }
}