package com.undoschool.booking.seeder;

import org.springframework.web.client.RestTemplate;
import java.util.*;

public class CourseSeeder {

    public static void main(String[] args) {

        RestTemplate rest = new RestTemplate();

        List<Map<String, Object>> courses = new ArrayList<>();

        // ---------------- COURSE 1 ----------------
        Map<String, Object> course1 = new HashMap<>();
        course1.put("courseName", "Java Spring Boot");
        course1.put("description", "Backend Development with Spring Boot");
        course1.put("durationInWeeks", 8);

        // ---------------- COURSE 2 ----------------
        Map<String, Object> course2 = new HashMap<>();
        course2.put("courseName", "Python Basics");
        course2.put("description", "Intro to Python Programming");
        course2.put("durationInWeeks", 6);

        // ---------------- COURSE 3 ----------------
        Map<String, Object> course3 = new HashMap<>();
        course3.put("courseName", "Data Structures");
        course3.put("description", "DSA for interviews");
        course3.put("durationInWeeks", 10);

        // ---------------- COURSE 4 ----------------
        Map<String, Object> course4 = new HashMap<>();
        course4.put("courseName", "Frontend Basics");
        course4.put("description", "HTML, CSS, JS basics");
        course4.put("durationInWeeks", 5);

        // Add all courses
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);

        // ---------------- SEND REQUESTS ----------------
        for (Map<String, Object> course : courses) {
            try {
                String response = rest.postForObject(
                        "http://localhost:8080/api/courses",
                        course,
                        String.class
                );

                System.out.println("Created: " + course.get("courseName"));

            } catch (Exception e) {
                System.out.println("Failed: " + course.get("courseName") + " -> " + e.getMessage());
            }
        }
    }
}