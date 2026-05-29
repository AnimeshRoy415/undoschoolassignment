package com.undoschool.booking.repository;

import com.undoschool.booking.entity.Booking;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface BookingRepository
        extends JpaRepository<Booking, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Booking> findByParentId(Long parentId);

    boolean existsByParentIdAndOfferingId(
            Long parentId,
            Long offeringId
    );
}