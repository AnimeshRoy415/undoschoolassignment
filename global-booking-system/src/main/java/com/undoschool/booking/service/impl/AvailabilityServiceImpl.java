package com.undoschool.booking.service.impl;

import com.undoschool.booking.dto.request.SessionRequestDTO;
import com.undoschool.booking.entity.Session;
import com.undoschool.booking.exception.BookingConflictException;
import com.undoschool.booking.repository.BookingRepository;
import com.undoschool.booking.repository.SessionRepository;
import com.undoschool.booking.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final SessionRepository sessionRepository;
    private final BookingRepository bookingRepository;

    @Override
    public void validateTeacherAvailability(
            Long teacherId,
            List<SessionRequestDTO> sessions,
            ZoneId teacherZone) {

        List<Session> existing =
                sessionRepository.findAllSessionsByTeacherId(teacherId);

        for (SessionRequestDTO request : sessions) {

            Instant start =
                    ZonedDateTime.of(
                                    request.getStartTime(),
                                    teacherZone)
                            .toInstant();

            Instant end =
                    ZonedDateTime.of(
                                    request.getEndTime(),
                                    teacherZone)
                            .toInstant();

            for (Session old : existing) {

                if (isOverlapping(
                        start,
                        end,
                        old.getStartTimeUtc(),
                        old.getEndTimeUtc())) {

                    throw new IllegalStateException(
                            "Teacher already booked");
                }
            }
        }
    }

    @Override
    public void validateParentAvailability(
            Long parentId,
            List<Session> incoming) {

        List<Session> existing =
                bookingRepository.findBookedSessionsByParent(parentId);

        for (Session newSession : incoming) {

            for (Session oldSession : existing) {

                if (isOverlapping(
                        newSession.getStartTimeUtc(),
                        newSession.getEndTimeUtc(),
                        oldSession.getStartTimeUtc(),
                        oldSession.getEndTimeUtc())) {

                    throw new BookingConflictException(
                            "Schedule conflict");
                }
            }
        }
    }

    private void validateConflicts(List<Session> existing,
                                   List<Session> incoming) {

        for (Session newSession : incoming) {
            for (Session oldSession : existing) {

                if (isOverlapping(oldSession, newSession)) {
                    throw new BookingConflictException(
                            "Session conflict detected: " +
                                    newSession.getStartTimeUtc()
                    );
                }
            }
        }
    }

    public boolean isOverlapping(
            Instant start1,
            Instant end1,
            Instant start2,
            Instant end2) {

        return start1.isBefore(end2)
                && end1.isAfter(start2);
    }

    private boolean isOverlapping(Session a, Session b) {
        return a.getStartTimeUtc().isBefore(b.getEndTimeUtc())
                && a.getEndTimeUtc().isAfter(b.getStartTimeUtc());
    }
}