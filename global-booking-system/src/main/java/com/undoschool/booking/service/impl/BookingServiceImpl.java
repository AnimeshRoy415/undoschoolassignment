package com.undoschool.booking.service.impl;

import com.undoschool.booking.dto.request.BookingRequest;
import com.undoschool.booking.dto.response.BookingResponse;
import com.undoschool.booking.entity.Booking;
import com.undoschool.booking.entity.Offering;
import com.undoschool.booking.entity.Parent;
import com.undoschool.booking.entity.Session;
import com.undoschool.booking.enums.BookingStatus;
import com.undoschool.booking.enums.OfferingStatus;
import com.undoschool.booking.exception.*;
import com.undoschool.booking.repository.BookingRepository;
import com.undoschool.booking.repository.OfferingRepository;
import com.undoschool.booking.repository.ParentRepository;
import com.undoschool.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final ParentRepository parentRepository;
    private final OfferingRepository offeringRepository;
    private final BookingRepository bookingRepository;

    @Override
    public BookingResponse bookOffering(Long parentId, BookingRequest request) {

        Parent parent = getParentWithLock(parentId);
        Offering offering = getOffering(request.getOfferingId());

        validateOfferingActive(offering);
        validateDuplicateBooking(parent.getId(), offering.getId());

        List<Session> incomingSessions = getOfferingSessions(offering);

        List<Session> existingSessions = getBookedSessions(parent);

        validateSessionConflicts(existingSessions, incomingSessions);

        Booking booking = createBooking(parent, offering);

        return buildResponse(booking);
    }

    // -------------------- FETCH METHODS --------------------

    private Parent getParentWithLock(Long parentId) {
        return parentRepository.findByIdForUpdate(parentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Parent not found: " + parentId));
    }

    private Offering getOffering(Long offeringId) {
        return offeringRepository.findById(offeringId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Offering not found: " + offeringId));
    }

    private List<Session> getOfferingSessions(Offering offering) {

        List<Session> sessions = offering.getSessions();

        if (sessions == null || sessions.isEmpty()) {
            throw new InvalidSessionException("Offering has no sessions");
        }

        return sessions;
    }

    private List<Session> getBookedSessions(Parent parent) {

        return bookingRepository
                .findParentBookings(parent.getId())
                .stream()
                .flatMap(b -> b.getOffering()
                        .getSessions()
                        .stream())
                .toList();
    }

    // -------------------- VALIDATIONS --------------------

    private void validateOfferingActive(Offering offering) {

        if (offering.getStatus() != OfferingStatus.ACTIVE) {
            throw new IllegalStateException("Offering is not ACTIVE");
        }
    }

    private void validateDuplicateBooking(Long parentId, Long offeringId) {

        if (bookingRepository.existsByParentIdAndOfferingId(parentId, offeringId)) {
            throw new DuplicateBookingException("Offering already booked");
        }
    }

    private void validateSessionConflicts(List<Session> existing,
                                          List<Session> incoming) {

        for (Session newSession : incoming) {
            for (Session oldSession : existing) {

                if (isOverlapping(oldSession, newSession)) {
                    throw new BookingConflictException(
                            "Session conflict detected for time range"
                    );
                }
            }
        }
    }

    private boolean isOverlapping(Session a, Session b) {

        return a.getStartTimeUtc().isBefore(b.getEndTimeUtc())
                && a.getEndTimeUtc().isAfter(b.getStartTimeUtc());
    }

    // -------------------- CREATE BOOKING --------------------

    private Booking createBooking(Parent parent, Offering offering) {

        Booking booking = Booking.builder()
                .parent(parent)
                .offering(offering)
                .status(BookingStatus.CONFIRMED)
                .bookedAt(Instant.now())
                .build();

        return bookingRepository.save(booking);
    }

    // -------------------- RESPONSE --------------------

    private BookingResponse buildResponse(Booking booking) {

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .parentId(booking.getParent().getId())
                .offeringId(booking.getOffering().getId())
                .status(booking.getStatus().name())
                .message("Booking created successfully")
                .build();
    }
}