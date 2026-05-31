package com.undoschool.booking.service.impl;

import com.undoschool.booking.dto.request.SessionRequestDTO;
import com.undoschool.booking.entity.Offering;
import com.undoschool.booking.entity.Session;
import com.undoschool.booking.enums.SessionStatus;
import com.undoschool.booking.util.TimezoneUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class SessionFactory {

    public Session create(
            SessionRequestDTO request,
            Offering offering,
            String teacherTimezone) {

        Instant startUtc =
                TimezoneUtil.convertLocalToUtc(
                        request.getStartTime(),
                        teacherTimezone);

        Instant endUtc =
                TimezoneUtil.convertLocalToUtc(
                        request.getEndTime(),
                        teacherTimezone);

        return Session.builder()
                .offering(offering)
                .startTimeUtc(startUtc)
                .endTimeUtc(endUtc)
                .status(SessionStatus.SCHEDULED)
                .build();
    }
}