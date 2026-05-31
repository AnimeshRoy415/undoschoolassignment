package com.undoschool.booking.repository;

import com.undoschool.booking.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByOfferingId(Long offeringId);

    List<Session> findByStartTimeUtcLessThanAndEndTimeUtcGreaterThan(
            Instant end,
            Instant start
    );

    @Query("SELECT s FROM Session s JOIN FETCH s.offering WHERE s.id = :id")
    Optional<Session> findByIdWithOffering(Long id);

    @Query("SELECT s FROM Session s JOIN FETCH s.offering WHERE s.offering.id = :offeringId")
    List<Session> findByOfferingIdWithOffering(Long offeringId);
}