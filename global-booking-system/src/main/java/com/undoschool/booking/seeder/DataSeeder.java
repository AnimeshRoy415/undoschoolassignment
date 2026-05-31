package com.undoschool.booking.seeder;

import org.springframework.web.client.RestTemplate;
import java.util.*;

public class DataSeeder {

    public static void main(String[] args) {

        RestTemplate rest = new RestTemplate();

        // ---------------- TEACHER ----------------
        Map<String, Object> teacher = new HashMap<>();
        teacher.put("firstName", "John");
        teacher.put("lastName", "Doe");
        teacher.put("email", "teacher@test.com");
        teacher.put("phoneNumber", "9999999999");
        teacher.put("timezone", "Asia/Kolkata");

        rest.postForObject("http://localhost:8080/api/teachers", teacher, String.class);

        // ---------------- COURSE ----------------
        Map<String, Object> course = new HashMap<>();
        course.put("courseName", "Java Spring Boot");
        course.put("description", "Backend course");
        course.put("durationInWeeks", 8);

        rest.postForObject("http://localhost:8080/api/courses", course, String.class);

        // ---------------- PARENTS (25 USERS) ----------------
        for (int i = 1; i <= 25; i++) {

            Map<String, Object> parent = new HashMap<>();
            parent.put("firstName", "Parent" + i);
            parent.put("lastName", "Test");
            parent.put("email", "parent" + i + "@test.com");
            parent.put("timezone", "Asia/Kolkata");
            parent.put("country", "India");
            parent.put("phoneNumber", "9000000" + i);

            try {
                rest.postForObject("http://localhost:8080/api/parents", parent, String.class);
                System.out.println("Created Parent " + i);
            } catch (Exception e) {
                System.out.println("Failed Parent " + i);
            }
        }
    }
}
