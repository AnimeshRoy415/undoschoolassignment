package com.undoschool.booking.controller;

import com.undoschool.booking.dto.request.OfferingRequestDTO;
import com.undoschool.booking.dto.response.OfferingResponseDTO;
import com.undoschool.booking.service.OfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offerings")
public class OfferingController {

    @Autowired
    private OfferingService offeringService;

    @PostMapping
    public ResponseEntity<OfferingResponseDTO> createOffering(
            @RequestBody OfferingRequestDTO request) {
        return ResponseEntity.ok(offeringService.createOffering(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferingResponseDTO> getOffering(@PathVariable Long id) {
        return ResponseEntity.ok(offeringService.getOfferingById(id));
    }

    @GetMapping
    public ResponseEntity<List<OfferingResponseDTO>> getAllOfferings() {
        return ResponseEntity.ok(offeringService.getAllOfferings());
    }
}