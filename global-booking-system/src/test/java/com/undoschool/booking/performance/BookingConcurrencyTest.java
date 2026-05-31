package com.undoschool.booking.performance;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BookingConcurrencyTest {

    private static final int TOTAL_REQUESTS = 20;
    private static final int THREAD_POOL_SIZE = 5;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        List<Long> responseTimes = Collections.synchronizedList(new ArrayList<>());

        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= TOTAL_REQUESTS; i++) {

            final int parentId = i;

            executor.submit(() -> {

                long requestStart = System.currentTimeMillis();

                try {
                    RestTemplate restTemplate = new RestTemplate();

                    String url = "http://localhost:8080/api/bookings?parentId=" + parentId;

                    Map<String, Object> requestBody = new HashMap<>();
                    requestBody.put("offeringId", 2);

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);

                    HttpEntity<Map<String, Object>> entity =
                            new HttpEntity<>(requestBody, headers);

                    ResponseEntity<String> response = restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            String.class
                    );

                    if (response.getStatusCode().is2xxSuccessful()) {
                        successCount.incrementAndGet();
                    } else {
                        failureCount.incrementAndGet();
                    }

                } catch (Exception e) {
                    failureCount.incrementAndGet();
                    System.out.println("FAILED parentId=" + parentId + " -> " + e.getMessage());
                }

                long requestEnd = System.currentTimeMillis();
                responseTimes.add(requestEnd - requestStart);
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);

        long totalTime = System.currentTimeMillis() - startTime;

        // ---------------- REPORT ----------------

        System.out.println("\n========== CONCURRENCY TEST REPORT ==========");
        System.out.println("Total Requests   : " + TOTAL_REQUESTS);
        System.out.println("Success Count    : " + successCount.get());
        System.out.println("Failure Count    : " + failureCount.get());
        System.out.println("Total Time (ms)  : " + totalTime);

        // Average response time
        double avgResponseTime = responseTimes.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0.0);

        System.out.println("Avg Response Time: " + avgResponseTime + " ms");

        System.out.println("=============================================\n");
    }
}