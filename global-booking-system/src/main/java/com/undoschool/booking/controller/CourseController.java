package com.undoschool.booking.controller;

import com.undoschool.booking.dto.ApiResponse;
import com.undoschool.booking.dto.request.CourseRequestDto;
import com.undoschool.booking.service.Impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseServiceImpl courseServiceImpl;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> addCourse(@RequestBody CourseRequestDto dto) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Course created", courseServiceImpl.addCourse(dto))
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllCourses() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Courses fetched successfully",
                        courseServiceImpl.getAllCourses()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getCourseById(@PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Course fetched successfully",
                        courseServiceImpl.getCourseById(id)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateCourse(
            @PathVariable Long id,
            @RequestBody CourseRequestDto courseRequestDto) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Course updated successfully",
                        courseServiceImpl.updateCourse(id, courseRequestDto)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCourse(@PathVariable Long id) {

        courseServiceImpl.deleteCourse(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Course deleted successfully",
                        null
                )
        );
    }
}