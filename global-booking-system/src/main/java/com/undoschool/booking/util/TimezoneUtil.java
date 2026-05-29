package com.undoschool.booking.util;

import java.time.*;

public class TimezoneUtil {

    public static Instant convertToUtc(
            String localDateTime,
            String timezone) {

        LocalDateTime parsed =
                LocalDateTime.parse(localDateTime);

        ZonedDateTime zonedDateTime =
                parsed.atZone(
                        ZoneId.of(timezone)
                );

        return zonedDateTime.toInstant();
    }

    public static String convertUtcToTimezone(
            Instant utcTime,
            String timezone) {

        return utcTime
                .atZone(ZoneId.of(timezone))
                .toString();
    }
}