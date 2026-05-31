package com.undoschool.booking.seeder;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class TeacherSeeder {

    public static void main(String[] args) {

        String[] timezones = {
                "Asia/Kolkata",
                "America/New_York",
                "Europe/London",
                "Asia/Singapore",
                "Australia/Sydney"
        };

        RestTemplate rest = new RestTemplate();

        String url = "http://localhost:8080/api/teachers";

        for (int i = 1; i <= 10; i++) {

            Map<String, Object> teacher = new HashMap<>();

            teacher.put("firstName", "Teacher" + i);
            teacher.put("lastName", "Global");
            teacher.put("email", "teacher" + i + "@test.com");
            teacher.put("phoneNumber", "8880000" + i);
            teacher.put("timezone", timezones[i % timezones.length]);

            rest.postForObject("http://localhost:8080/api/teachers", teacher, String.class);

            System.out.println("Created Teacher " + i + " -> " + teacher.get("timezone"));
        }
    }
}