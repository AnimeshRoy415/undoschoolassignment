package com.undoschool.booking.seeder;

import org.springframework.web.client.RestTemplate;

import java.util.*;

public class ParentSeeder {

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/parents";

        List<String> firstNames = Arrays.asList(
                "Aarav", "Vivaan", "Aditya", "Vihaan", "Arjun",
                "Ananya", "Ishita", "Saanvi", "Diya", "Meera",
                "Rahul", "Amit", "Karan", "Rohan", "Suresh",
                "John", "Michael", "David", "Chris", "Alex",
                "Emma", "Sophia", "Olivia", "Ava", "Mia"
        );

        List<String> lastNames = Arrays.asList(
                "Sharma", "Verma", "Gupta", "Singh", "Patel",
                "Roy", "Das", "Banerjee", "Kumar", "Mehta"
        );

        String[] timezones = {
                "Asia/Kolkata",
                "Asia/Singapore",
                "Europe/London",
                "America/New_York",
                "Australia/Sydney"
        };

        String[] countries = {
                "India",
                "Singapore",
                "UK",
                "USA",
                "Australia"
        };

        Random random = new Random();

        for (int i = 1; i <= 25; i++) {

            String firstName = firstNames.get(random.nextInt(firstNames.size()));
            String lastName = lastNames.get(random.nextInt(lastNames.size()));

            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + i + "@testmail.com";

            String timezone = timezones[random.nextInt(timezones.length)];
            String country = countries[random.nextInt(countries.length)];

            String phone = "+91" + (7000000000L + random.nextInt(999999999));

            Map<String, Object> request = new HashMap<>();
            request.put("firstName", firstName);
            request.put("lastName", lastName);
            request.put("email", email);
            request.put("timezone", timezone);
            request.put("country", country);
            request.put("phoneNumber", phone);

            try {
                restTemplate.postForObject(url, request, String.class);
                System.out.println("Created Parent: " + firstName + " " + lastName + " | " + country + " | " + timezone);

            } catch (Exception e) {
                System.out.println("Failed Parent " + i + " -> " + e.getMessage());
            }
        }
    }
}