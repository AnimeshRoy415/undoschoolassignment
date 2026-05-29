package com.undoschool.booking.repository;

import com.undoschool.booking.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    List<SessionEntity> findByOfferingId(Long offeringId);
}
