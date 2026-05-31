package com.undoschool.booking.mapper;

import com.undoschool.booking.dto.response.SessionResponse;
import com.undoschool.booking.entity.Session;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SessionMapper {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private SessionMapper() {
    }

    public static SessionResponse toResponse(
            Session session,
            String timezone
    ) {

        ZoneId zone = ZoneId.of(timezone);

        return SessionResponse.builder()
                .sessionId(session.getId())
                .startTimeUtc(
                        session.getStartTimeUtc()
                                .atZone(zone)
                                .format(FORMATTER)
                )
                .endTimeUtc(
                        session.getEndTimeUtc()
                                .atZone(zone)
                                .format(FORMATTER)
                )
                .status(session.getStatus().name())
                .build();
    }
}