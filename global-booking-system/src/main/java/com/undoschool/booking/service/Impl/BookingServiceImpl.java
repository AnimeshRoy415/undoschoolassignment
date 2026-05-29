package com.undoschool.booking.service.Impl;

import com.undoschool.booking.dto.BookingRequest;
import com.undoschool.booking.entity.*;
import com.undoschool.booking.exception.ConflictException;
import com.undoschool.booking.exception.ResourceNotFoundException;
import com.undoschool.booking.repository.*;
import com.undoschool.booking.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private static final Logger logger =
            LoggerFactory.getLogger(BookingServiceImpl.class);

    private final BookingRepository bookingRepository;
    private final ParentRepository parentRepository;
    private final OfferingRepository offeringRepository;
    private final SessionRepository sessionRepository;

    @Override
    @Transactional
    public Booking bookOffering(BookingRequest request) {

        logger.info("Booking started for parent {}", request.getParentId());

        Parent parent = parentRepository.findById(request.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found"));

        Offering offering = offeringRepository.findById(request.getOfferingId())
                .orElseThrow(() -> new ResourceNotFoundException("Offering not found"));

        if (bookingRepository.existsByParentIdAndOfferingId(parent.getId(), offering.getId())) {
            throw new ConflictException("Offering already booked");
        }

        if (offering.getEnrolledStudents() >= offering.getMaxStudents()) {
            throw new ConflictException("Offering is full");
        }

        List<SessionEntity> requestedSessions =
                sessionRepository.findByOfferingId(offering.getId());

        List<Booking> existingBookings =
                bookingRepository.findByParentId(parent.getId());

        for (Booking booking : existingBookings) {

            List<SessionEntity> bookedSessions =
                    sessionRepository.findByOfferingId(
                            booking.getOffering().getId()
                    );

            for (SessionEntity r : requestedSessions) {
                for (SessionEntity b : bookedSessions) {

                    boolean overlap =
                            r.getStartTimeUtc().isBefore(b.getEndTimeUtc())
                                    && b.getStartTimeUtc().isBefore(r.getEndTimeUtc());

                    if (overlap) {
                        throw new ConflictException("Booking conflict detected");
                    }
                }
            }
        }

        Booking booking = new Booking();
        booking.setParent(parent);
        booking.setOffering(offering);

        offering.setEnrolledStudents(offering.getEnrolledStudents() + 1);
        offeringRepository.save(offering);

        logger.info("Booking successful for parent {}", parent.getId());

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }
}