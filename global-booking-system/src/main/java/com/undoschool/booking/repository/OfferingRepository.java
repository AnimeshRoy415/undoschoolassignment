package com.undoschool.booking.repository;

import com.undoschool.booking.entity.Offering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferingRepository extends JpaRepository<Offering, Long> {}
