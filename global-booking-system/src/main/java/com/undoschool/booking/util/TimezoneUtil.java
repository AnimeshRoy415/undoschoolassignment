package com.undoschool.booking.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimezoneUtil {

    public static String convertUtcToLocal(Instant utc, String timezone) {

        ZonedDateTime zonedDateTime = utc.atZone(ZoneOffset.UTC)
                .withZoneSameInstant(ZoneId.of(timezone));

        return zonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static String toLocal(Instant instant, String zone) {
        return instant.atZone(ZoneId.of(zone))
                    .toLocalDateTime()
                    .toString();
    }

    public static Instant convertLocalToUtc(
            LocalDateTime local,
            String timezone) {

        return local
                .atZone(ZoneId.of(timezone))
                .toInstant();
    }

    public static void validateTimezone(String timezone) {
        try {
            ZoneId.of(timezone);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid timezone: " + timezone);
        }
    }
}