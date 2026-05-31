-- =========================================
-- COURSES
-- =========================================

CREATE TABLE courses (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         created_at TIMESTAMP NOT NULL,
                         updated_at TIMESTAMP NOT NULL,

                         course_name VARCHAR(255) NOT NULL,
                         description VARCHAR(2000),
                         duration_in_weeks INT
                     );

-- =========================================
-- TEACHERS
-- =========================================

CREATE TABLE teachers (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          created_at TIMESTAMP NOT NULL,
                          updated_at TIMESTAMP NOT NULL,

                          first_name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          phone_number VARCHAR(255) UNIQUE,
                          timezone VARCHAR(100) NOT NULL
                      );

-- =========================================
-- PARENTS
-- =========================================

CREATE TABLE parents (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         created_at TIMESTAMP NOT NULL,
                         updated_at TIMESTAMP NOT NULL,

                         first_name VARCHAR(255),
                         last_name VARCHAR(255),
                         email VARCHAR(255) NOT NULL UNIQUE,
                         timezone VARCHAR(100) NOT NULL,
                         country VARCHAR(255),
                         phone_number VARCHAR(255) UNIQUE
                     );

-- =========================================
-- OFFERINGS
-- =========================================

CREATE TABLE offerings (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           created_at TIMESTAMP NOT NULL,
                           updated_at TIMESTAMP NOT NULL,

                           offering_name VARCHAR(255) NOT NULL,
                           batch_type VARCHAR(255),
                           status VARCHAR(50) NOT NULL,
                           teacher_timezone VARCHAR(100) NOT NULL,

                           course_id BIGINT NOT NULL,
                           teacher_id BIGINT NOT NULL,

                           CONSTRAINT fk_offering_course
                               FOREIGN KEY (course_id)
                                   REFERENCES courses(id),

                           CONSTRAINT fk_offering_teacher
                               FOREIGN KEY (teacher_id)
                                   REFERENCES teachers(id)
                       );

-- =========================================
-- SESSIONS
-- =========================================

CREATE TABLE sessions (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          created_at TIMESTAMP NOT NULL,
                          updated_at TIMESTAMP NOT NULL,

                          offering_id BIGINT NOT NULL,

                          start_time_utc TIMESTAMP NOT NULL,
                          end_time_utc TIMESTAMP NOT NULL,

                          status VARCHAR(50) NOT NULL,

                          CONSTRAINT fk_session_offering
                              FOREIGN KEY (offering_id)
                                  REFERENCES offerings(id)
                      );

CREATE INDEX idx_session_offering
    ON sessions(offering_id);

CREATE INDEX idx_session_time
    ON sessions(start_time_utc, end_time_utc);

CREATE INDEX idx_session_offering_time
    ON sessions(offering_id, start_time_utc);

-- =========================================
-- BOOKINGS
-- =========================================

CREATE TABLE bookings (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          created_at TIMESTAMP NOT NULL,
                          updated_at TIMESTAMP NOT NULL,

                          parent_id BIGINT NOT NULL,
                          offering_id BIGINT NOT NULL,

                          status VARCHAR(50) NOT NULL,
                          booked_at TIMESTAMP NOT NULL,

                          booking_reference VARCHAR(255) NOT NULL UNIQUE,

                          CONSTRAINT fk_booking_parent
                              FOREIGN KEY (parent_id)
                                  REFERENCES parents(id),

                          CONSTRAINT fk_booking_offering
                              FOREIGN KEY (offering_id)
                                  REFERENCES offerings(id),

                          CONSTRAINT uk_parent_offering
                              UNIQUE(parent_id, offering_id)
                      );

CREATE INDEX idx_booking_parent
    ON bookings(parent_id);