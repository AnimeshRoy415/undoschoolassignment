# Global Booking System

## Project Overview

Global Booking System is a backend application built using Java and Spring Boot that enables teachers to create course offerings and allows parents to book those offerings.

The system supports:

* Course management
* Teacher management
* Parent management
* Offering management
* Session scheduling
* Booking management
* Timezone-aware scheduling
* Concurrency-safe booking operations
* Conflict detection for teachers and parents

A teacher can create offerings consisting of multiple sessions. Sessions are stored in UTC internally and displayed to users in their local timezone.

---

# Tech Stack

## Backend

* Java 17
* Spring Boot 3
* Spring Data JPA
* Hibernate

## Database

* MySQL(8.0)

## Build Tool

* Maven

## API Testing

* Swagger/OpenAPI

## Concurrency Testing

* Java ExecutorService

---

# Project Structure

```text
src/main/java/com/undoschool/booking

# Project Structure

```text
src/main/java/com/undoschool/booking

├── controller          # REST API endpoints
├── service             # Service interfaces
├── service/impl        # Service implementations
├── repository          # JPA repositories
├── entity              # Database entities
├── dto                 # Request/Response DTOs
├── mapper              # Entity ↔ DTO conversion
├── enums               # Application enums
├── exception           # Custom exceptions & handlers
├── util                # Utility classes
└── config              # Application configuration

src/test/java/com/undoschool/booking

├── seeder
│   ├── CourseSeeder
│   ├── TeacherSeeder
│   ├── ParentSeeder
│   └── DataSeeder
│
└── performance
    └── BookingConcurrencyTest
```

## Enums

The application uses enums to maintain type safety and avoid hard-coded values.

### OfferingStatus

```java
ACTIVE
INACTIVE
CANCELLED
```

### SessionStatus

```java
SCHEDULED
COMPLETED
CANCELLED
```

### BookingStatus

```java
CONFIRMED
CANCELLED
PENDING
```

Using enums improves code readability, maintainability, and prevents invalid status values from being persisted in the database.

```

# Setup Instructions

## Clone Repository

bash
git clone https://github.com/AnimeshRoy415/undoschoolassignment
cd global-booking-system
```

## Configure Database

Create a MySQL database:

```sql
CREATE DATABASE booking_system;
```

## Update application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/global_booking
spring.datasource.username=root
spring.datasource.password=Admin@123

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Build Project

```bash
mvn clean install
```

## Run Application

```bash
mvn spring-boot:run
```

Application starts on:

```text
http://localhost:8080
```

---

# Environment Variables Required

| Variable    | Description       |
| ----------- | ----------------- |
| DB_URL      | jdbc:mysql://localhost:3306/global_booking      |
| DB_USERNAME | root |
| DB_PASSWORD | Admin@123 |

Example:

```properties
DB_URL=jdbc:mysql://localhost:3306/booking_system
DB_USERNAME=root
DB_PASSWORD=Admin@123
```

---

# API Documentation

## Teacher APIs

### Create Teacher

```http
POST /api/teachers
```

### Get Teacher

```http
GET /api/teachers/{id}
```

### Get All Teachers

```http
GET /api/teachers
```

### Update Teacher

```http
PUT /api/teachers/{id}
```

### Delete Teacher

```http
DELETE /api/teachers/{id}
```

---

## Parent APIs

### Create Parent

```http
POST /api/parents
```

### Get Parent

```http
GET /api/parents/{id}
```

### Get All Parents

```http
GET /api/parents
```

### Update Parent

```http
PUT /api/parents/{id}
```

### Delete Parent

```http
DELETE /api/parents/{id}
```

---

## Course APIs

### Create Course

```http
POST /api/courses
```

### Get Course

```http
GET /api/courses/{id}
```

### Get All Courses

```http
GET /api/courses
```

### Update Course

```http
PUT /api/courses/{id}
```

### Delete Course

```http
DELETE /api/courses/{id}
```

---

## Offering APIs

### Create Offering

```http
POST /api/offerings
```

### Get Offering

```http
GET /api/offerings/{id}?timezone=Asia/Kolkata
```

### Get All Offerings

```http
GET /api/offerings?timezone=America/New_York
```

### Update Offering

```http
PUT /api/offerings/{id}
```

### Delete Offering

```http
DELETE /api/offerings/{id}
```

---

## Booking APIs

### Book Offering

```http
POST /api/bookings?parentId=1
```

### Get All Bookings

```http
GET /api/bookings
```

### Get Parent Bookings

```http
GET /api/bookings/parent/{parentId}
```

---

# Database Schema Overview

The database schema is available under:

database/schema.sql

Core tables:
- teachers
- parents
- courses
- offerings
- sessions
- bookings

Relationships:
- Teacher → Offerings (1:N)
- Course → Offerings (1:N)
- Offering → Sessions (1:N)
- Parent → Bookings (1:N)
- Offering → Bookings (1:N)

## Teacher

| Column       | Type    |
| ------------ | ------- |
| id           | BIGINT  |
| first_name   | VARCHAR |
| last_name    | VARCHAR |
| email        | VARCHAR |
| phone_number | VARCHAR |
| timezone     | VARCHAR |

---

## Parent

| Column       | Type    |
| ------------ | ------- |
| id           | BIGINT  |
| first_name   | VARCHAR |
| last_name    | VARCHAR |
| email        | VARCHAR |
| phone_number | VARCHAR |
| timezone     | VARCHAR |
| country      | VARCHAR |

---

## Course

| Column            | Type    |
| ----------------- | ------- |
| id                | BIGINT  |
| course_name       | VARCHAR |
| description       | VARCHAR |
| duration_in_weeks | INT     |

---

## Offering

| Column           | Type    |
| ---------------- | ------- |
| id               | BIGINT  |
| offering_name    | VARCHAR |
| batch_type       | VARCHAR |
| status           | VARCHAR |
| teacher_timezone | VARCHAR |
| teacher_id       | FK      |
| course_id        | FK      |

---

## Session

| Column         | Type      |
| -------------- | --------- |
| id             | BIGINT    |
| start_time_utc | TIMESTAMP |
| end_time_utc   | TIMESTAMP |
| status         | VARCHAR   |
| offering_id    | FK        |

---

## Booking

| Column      | Type      |
| ----------- | --------- |
| id          | BIGINT    |
| booked_at   | TIMESTAMP |
| status      | VARCHAR   |
| parent_id   | FK        |
| offering_id | FK        |

---

# Assumptions Made

1. A teacher cannot have overlapping sessions across offerings.
2. A parent cannot book overlapping sessions.
3. Sessions are stored in UTC.
4. All users provide a valid IANA timezone.
5. An offering can contain multiple sessions.
6. A booking confirms participation in all sessions of an offering.
7. Offerings with existing bookings cannot be deleted.
8. Teacher timezone is fixed for an offering.

---

# Concurrency Handling Approach

The booking workflow is designed to be concurrency-safe.

## Approach Used

### Pessimistic Locking

Parent records are locked during booking:

```java
parentRepository.findByIdForUpdate(parentId)
```

This prevents concurrent requests from creating duplicate bookings.

### Duplicate Booking Validation

```java
existsByParentIdAndOfferingId(...)
```

Prevents the same parent from booking the same offering multiple times.

### Conflict Detection

Before booking:

* Existing booked sessions are loaded
* Incoming offering sessions are loaded
* Session overlap validation is executed

Result:

* No double booking
* No overlapping schedules
* Consistent booking state

---

# Timezone Handling Approach

## Storage Strategy

All session times are stored in UTC.

Example:

```text
Teacher Time:
01-Jun-2026 10:00 AM Asia/Kolkata

Stored:
2026-06-01T04:30:00Z
```

---

## Display Strategy

Client provides timezone:

```http
GET /api/offerings/1?timezone=America/New_York
```

Backend converts UTC to requested timezone.

Example:

```text
Stored UTC:
2026-06-01T04:30:00Z

Teacher View:
10:00 AM Asia/Kolkata

Parent View:
12:30 AM America/New_York
```

This ensures users always see schedules in their own local time.

---

# Performance Testing

A multithreaded concurrency test was implemented using:

```java
ExecutorService
```

Test simulates:

* 20 concurrent parents
* Multiple booking requests
* Response time tracking
* Success/failure metrics

Sample output:

```text
========== CONCURRENCY TEST REPORT ==========
Total Requests   : 20
Success Count    : 16
Failure Count    : 04
Avg Response Time: 58 ms
=============================================
```

---

# Steps to Run Locally

1. Install Java 17
2. Install MySQL (8.0.46.0)
3. Create database
4. Configure application.properties
5. Run migrations (if applicable)
6. Build project

```bash
mvn clean install
```

7. Start application

```bash
mvn spring-boot:run
```

8. Open Swagger

```text
http://localhost:8080/swagger-ui/index.html
```

9. Test APIs using Postman or Swagger UI.
