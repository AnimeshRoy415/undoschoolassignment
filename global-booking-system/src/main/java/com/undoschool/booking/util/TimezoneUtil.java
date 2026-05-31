package com.undoschool.booking.util;

import java.time.*;

public final class TimezoneUtil {

    private TimezoneUtil() {
    }

    public static Instant toUtc(
            LocalDateTime localDateTime,
            String timezone
    ) {

        return localDateTime
                .atZone(ZoneId.of(timezone))
                .toInstant();
    }

    public static ZonedDateTime toTimezone(
            Instant utcTime,
            String timezone
    ) {

        return utcTime.atZone(
                ZoneId.of(timezone)
        );
    }
}