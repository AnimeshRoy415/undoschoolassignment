package com.undoschool.booking.repository;

import com.undoschool.booking.entity.ParentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<ParentEntity, Long> {}