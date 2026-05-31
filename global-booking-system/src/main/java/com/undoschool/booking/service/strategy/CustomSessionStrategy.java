package com.undoschool.booking.service.strategy;

import com.undoschool.booking.dto.request.CreateOfferingRequest;
import com.undoschool.booking.entity.Offering;
import com.undoschool.booking.entity.Session;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CustomSessionStrategy
        implements SessionGenerationStrategy {

    @Override
    public String getScheduleCode() {
        return "CUSTOM";
    }

    @Override
    public List<Session> generateSessions(
            Offering offering,
            CreateOfferingRequest request) {

        return Collections.emptyList();
    }
}