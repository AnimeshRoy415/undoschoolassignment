package com.undoschool.booking.controller;

import com.undoschool.booking.dto.common.ApiResponse;
import com.undoschool.booking.dto.request.CourseRequestDTO;
import com.undoschool.booking.dto.response.CourseResponseDTO;
import com.undoschool.booking.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // ---------------- CREATE COURSE ----------------
    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponseDTO>> createCourse(
            @RequestBody CourseRequestDTO request) {

        CourseResponseDTO response = courseService.createCourse(request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Course created successfully", response)
        );
    }

    // ---------------- GET BY ID ----------------
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponseDTO>> getCourseById(
            @PathVariable Long id) {

        CourseResponseDTO response = courseService.getCourseById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Course fetched successfully", response)
        );
    }

    // ---------------- GET ALL ----------------
    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponseDTO>>> getAllCourses() {

        List<CourseResponseDTO> response = courseService.getAllCourses();

        return ResponseEntity.ok(
                new ApiResponse<>(true, "All courses fetched", response)
        );
    }

    // ---------------- UPDATE ----------------
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponseDTO>> updateCourse(
            @PathVariable Long id,
            @RequestBody CourseRequestDTO request) {

        CourseResponseDTO response = courseService.updateCourse(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Course updated successfully", response)
        );
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCourse(
            @PathVariable Long id) {

        courseService.deleteCourse(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Course deleted successfully", "Deleted course id: " + id)
        );
    }
}