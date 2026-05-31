package com.undoschool.booking.controller;


import com.undoschool.booking.dto.request.ParentRequestDTO;
import com.undoschool.booking.dto.response.ParentResponseDTO;
import com.undoschool.booking.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    @Autowired
    private ParentService parentService;

    @PostMapping
    public ResponseEntity<ParentResponseDTO> createParent(@RequestBody ParentRequestDTO request) {
        return ResponseEntity.ok(parentService.createParent(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentResponseDTO> getParent(@PathVariable Long id) {
        return ResponseEntity.ok(parentService.getParentById(id));
    }

    @GetMapping
    public ResponseEntity<List<ParentResponseDTO>> getAllParents() {
        return ResponseEntity.ok(parentService.getAllParents());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParentResponseDTO> updateParent(
            @PathVariable Long id,
            @RequestBody ParentRequestDTO request) {
        return ResponseEntity.ok(parentService.updateParent(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParent(@PathVariable Long id) {
        parentService.deleteParent(id);
        return ResponseEntity.ok("Parent deleted successfully");
    }
}