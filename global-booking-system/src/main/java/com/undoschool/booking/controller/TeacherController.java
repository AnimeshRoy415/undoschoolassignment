package com.undoschool.booking.controller;

import com.undoschool.booking.dto.ApiResponse;
import com.undoschool.booking.dto.request.TeacherRequestDto;
import com.undoschool.booking.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> addTeacher(@RequestBody TeacherRequestDto teacherRequestDto) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Teacher added successfully",
                        teacherService.addTeacher(teacherRequestDto)
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllTeachers() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Teachers fetched successfully",
                        teacherService.getAllTeachers()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getTeacherById(@PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Teacher fetched successfully",
                        teacherService.getTeacherById(id)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateTeacher(
            @PathVariable Long id,
            @RequestBody TeacherRequestDto teacherRequestDto) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Teacher updated successfully",
                        teacherService.updateTeacher(id, teacherRequestDto)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteTeacher(@PathVariable Long id) {

        teacherService.deleteTeacher(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Teacher deleted successfully",
                        null
                )
        );
    }
}