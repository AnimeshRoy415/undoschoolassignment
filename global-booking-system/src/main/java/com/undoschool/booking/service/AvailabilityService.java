package com.undoschool.booking.service;

import com.undoschool.booking.dto.request.SessionRequestDTO;
import com.undoschool.booking.entity.Session;

import java.time.ZoneId;
import java.util.List;

public interface AvailabilityService {

    void validateTeacherAvailability(
            Long teacherId,
            List<SessionRequestDTO> sessions,
            ZoneId teacherZone
    );

    void validateParentAvailability(
            Long parentId,
            List<Session> sessions
    );
}