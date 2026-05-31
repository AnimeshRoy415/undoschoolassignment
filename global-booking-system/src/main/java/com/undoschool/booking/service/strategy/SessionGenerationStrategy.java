package com.undoschool.booking.service.strategy;

import com.undoschool.booking.dto.request.CreateOfferingRequest;
import com.undoschool.booking.entity.Offering;
import com.undoschool.booking.entity.Session;

import java.util.List;

public interface SessionGenerationStrategy {

    String getScheduleCode();

    List<Session> generateSessions(
            Offering offering,
            CreateOfferingRequest request
    );
}