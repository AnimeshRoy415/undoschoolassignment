package com.undoschool.booking.controller;

import com.undoschool.booking.dto.common.ApiResponse;
import com.undoschool.booking.dto.TeacherRequestDTO;
import com.undoschool.booking.dto.TeacherResponseDTO;
import com.undoschool.booking.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    // ---------------- CREATE TEACHER ----------------
    @PostMapping
    public ResponseEntity<ApiResponse<TeacherResponseDTO>> createTeacher(
            @RequestBody TeacherRequestDTO request) {

        TeacherResponseDTO response = teacherService.createTeacher(request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Teacher created successfully", response)
        );
    }

    // ---------------- GET BY ID ----------------
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherResponseDTO>> getTeacher(
            @PathVariable Long id) {

        TeacherResponseDTO response = teacherService.getTeacherById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Teacher fetched successfully", response)
        );
    }

    // ---------------- GET ALL ----------------
    @GetMapping
    public ResponseEntity<ApiResponse<List<TeacherResponseDTO>>> getAllTeachers() {

        List<TeacherResponseDTO> response = teacherService.getAllTeachers();

        return ResponseEntity.ok(
                new ApiResponse<>(true, "All teachers fetched", response)
        );
    }

    // ---------------- UPDATE ----------------
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherResponseDTO>> updateTeacher(
            @PathVariable Long id,
            @RequestBody TeacherRequestDTO request) {

        TeacherResponseDTO response = teacherService.updateTeacher(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Teacher updated successfully", response)
        );
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTeacher(
            @PathVariable Long id) {

        teacherService.deleteTeacher(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Teacher deleted successfully", "Deleted teacher id: " + id)
        );
    }
}