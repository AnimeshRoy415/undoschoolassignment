package com.undoschool.booking.service.impl;

import com.undoschool.booking.dto.request.BookingRequestDTO;
import com.undoschool.booking.dto.response.BookingResponseDTO;
import com.undoschool.booking.entity.Booking;
import com.undoschool.booking.entity.Offering;
import com.undoschool.booking.entity.Parent;
import com.undoschool.booking.entity.Session;
import com.undoschool.booking.enums.BookingStatus;
import com.undoschool.booking.enums.OfferingStatus;
import com.undoschool.booking.exception.*;
import com.undoschool.booking.mapper.BookingMapper;
import com.undoschool.booking.repository.BookingRepository;
import com.undoschool.booking.repository.OfferingRepository;
import com.undoschool.booking.repository.ParentRepository;
import com.undoschool.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final ParentRepository parentRepository;
    private final OfferingRepository offeringRepository;
    private final BookingRepository bookingRepository;
    @Autowired
    private AvailabilityServiceImpl availabilityService;

    @Override
    public BookingResponseDTO bookOffering(Long parentId, BookingRequestDTO request) {

        // 1. LOCK PARENT (prevents concurrent booking)
        Parent parent = parentRepository.findByIdForUpdate(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found: " + parentId));

        // 2. FETCH OFFERING WITH SESSIONS (avoid lazy loading issues)
        Offering offering = offeringRepository.findByIdWithSessions(request.getOfferingId())
                .orElseThrow(() -> new ResourceNotFoundException("Offering not found: " + request.getOfferingId()));

        // 3. VALIDATE OFFERING STATUS
        if (offering.getStatus() != OfferingStatus.ACTIVE) {
            throw new IllegalStateException("Offering is not active");
        }

        // 4. PREVENT DUPLICATE BOOKING
        if (bookingRepository.existsByParentIdAndOfferingId(parentId, offering.getId())) {
            throw new DuplicateBookingException("Offering already booked by this parent");
        }

        // 5. GET DATA
        List<Session> incomingSessions = offering.getSessions();

        if (incomingSessions == null || incomingSessions.isEmpty()) {
            throw new InvalidSessionException("No sessions found in offering");
        }

//        List<Session> existingSessions =
//                bookingRepository.findBookedSessionsByParent(parentId);

        // 6. SORT (optimization step)
        incomingSessions.sort(Comparator.comparing(Session::getStartTimeUtc));
//        existingSessions.sort(Comparator.comparing(Session::getStartTimeUtc));

        // 7. CHECK CONFLICTS
        availabilityService.validateParentAvailability(parentId, incomingSessions);

        // 8. CREATE BOOKING
        Booking booking = Booking.builder()
                .parent(parent)
                .offering(offering)
                .status(BookingStatus.CONFIRMED)
                .bookedAt(Instant.now())
                .build();

        Booking saved = bookingRepository.save(booking);

        // 9. RESPONSE
        return BookingMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDTO> getAllBookings() {

        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream()
                .map(BookingMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDTO> getBookingsByParent(Long parentId) {

        List<Booking> bookings = bookingRepository.findByParentId(parentId);

        return bookings.stream()
                .map(BookingMapper::toDto)
                .toList();
    }
}