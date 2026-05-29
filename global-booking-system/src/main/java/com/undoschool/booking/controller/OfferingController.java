package com.undoschool.booking.controller;

import com.undoschool.booking.dto.ApiResponse;
import com.undoschool.booking.dto.request.OfferingRequestDto;
import com.undoschool.booking.service.OfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offerings")
public class OfferingController {

    @Autowired
    private OfferingService offeringService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> addOffering(@RequestBody OfferingRequestDto dto) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Offering created successfully",
                        offeringService.addOffering(dto)
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllOfferings() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Offerings fetched successfully",
                        offeringService.getAllOfferings()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getOfferingById(@PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Offering fetched successfully",
                        offeringService.getOfferingById(id)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateOffering(
            @PathVariable Long id,
            @RequestBody OfferingRequestDto dto) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Offering updated successfully",
                        offeringService.updateOffering(id, dto)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteOffering(@PathVariable Long id) {

        offeringService.deleteOffering(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Offering deleted successfully",
                        null
                )
        );
    }
}