package com.undoschool.booking.service.impl;

import com.undoschool.booking.dto.request.OfferingRequestDTO;
import com.undoschool.booking.dto.request.SessionRequestDTO;
import com.undoschool.booking.dto.response.OfferingResponseDTO;
import com.undoschool.booking.dto.response.SessionResponseDTO;
import com.undoschool.booking.entity.*;
import com.undoschool.booking.enums.OfferingStatus;
import com.undoschool.booking.enums.SessionStatus;
import com.undoschool.booking.mapper.OfferingMapper;
import com.undoschool.booking.repository.*;
import com.undoschool.booking.service.AvailabilityService;
import com.undoschool.booking.service.OfferingService;
import com.undoschool.booking.util.TimezoneUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferingServiceImpl implements OfferingService {

    @Autowired
    private OfferingRepository offeringRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private OfferingMapper offeringMapper;

    @Autowired
    private AvailabilityServiceImpl availabilityServiceImpl;

    // ======================================================
    // CREATE OFFERING
    // ======================================================
    @Override
    @Transactional
    public OfferingResponseDTO createOffering(OfferingRequestDTO request) {

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        ZoneId teacherZone = ZoneId.of(teacher.getTimezone());

        // STEP 1: FETCH EXISTING TEACHER SESSIONS
        List<Session> existingSessions =
                sessionRepository.findAllSessionsByTeacherId(teacher.getId());

        // STEP 2: VALIDATE TEACHER SCHEDULE CONFLICT
        validateTeacherScheduleConflict(request.getSessions(), existingSessions, teacherZone);

        // STEP 3: CREATE OFFERING
        Offering offering = Offering.builder()
                .offeringName(request.getOfferingName())
                .batchType(request.getBatchType())
                .status(OfferingStatus.ACTIVE)
                .course(course)
                .teacher(teacher)
                .teacherTimezone(teacher.getTimezone())
                .build();

        Offering savedOffering = offeringRepository.save(offering);

        // STEP 4: CREATE SESSIONS (UTC STORAGE)
        List<Session> sessions = new ArrayList<>();

        for (SessionRequestDTO s : request.getSessions()) {

            Instant startUtc = ZonedDateTime.of(s.getStartTime(), teacherZone).toInstant();
            Instant endUtc = ZonedDateTime.of(s.getEndTime(), teacherZone).toInstant();

            Session session = Session.builder()
                    .offering(savedOffering)
                    .startTimeUtc(startUtc)
                    .endTimeUtc(endUtc)
                    .status(SessionStatus.SCHEDULED)
                    .build();

            sessions.add(sessionRepository.save(session));
        }

        savedOffering.setSessions(sessions);

        return offeringMapper.mapToDTO(savedOffering, teacher.getTimezone());
    }

    // ======================================================
    // GET BY ID
    // ======================================================
    @Override
    public OfferingResponseDTO getOfferingById(Long id, String timeZone) {

        Offering offering = offeringRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offering not found"));

        return offeringMapper.mapToDTO(offering, timeZone);
    }

    // ======================================================
    // GET ALL
    // ======================================================
    @Override
    public List<OfferingResponseDTO> getAllOfferings(String timeZone) {

        return offeringRepository.findAll()
                .stream()
                .map(offering -> offeringMapper.mapToDTO(offering, timeZone))
                .collect(Collectors.toList());
    }

    // ======================================================
    // TEACHER CONFLICT VALIDATION
    // ======================================================
    private void validateTeacherScheduleConflict(List<SessionRequestDTO> newSessions,
                                                 List<Session> existingSessions,
                                                 ZoneId teacherZone) {

        List<TimeRange> existingRanges = existingSessions.stream()
                .map(s -> new TimeRange(s.getStartTimeUtc(), s.getEndTimeUtc()))
                .toList();

        for (SessionRequestDTO req : newSessions) {

            Instant newStart = ZonedDateTime.of(req.getStartTime(), teacherZone).toInstant();
            Instant newEnd = ZonedDateTime.of(req.getEndTime(), teacherZone).toInstant();

            for (TimeRange range : existingRanges) {

                if (availabilityServiceImpl.isOverlapping(newStart, newEnd, range.start, range.end)) {
                    throw new IllegalStateException(
                            "Teacher already has an offering in this time slot"
                    );
                }
            }
        }
    }


    @Override
    @Transactional
    public OfferingResponseDTO updateOffering(Long id, OfferingRequestDTO request) {

        Offering offering = offeringRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offering not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        ZoneId teacherZone = ZoneId.of(teacher.getTimezone());

        // STEP 1: OPTIONAL SAFETY CHECK (recommended)
        List<Session> existingSessions =
                sessionRepository.findAllSessionsByTeacherId(teacher.getId());

        // remove current offering sessions from conflict check
        existingSessions.removeAll(offering.getSessions());

        validateTeacherScheduleConflict(
                request.getSessions(),
                existingSessions,
                teacherZone
        );

        // STEP 2: UPDATE BASIC FIELDS
        offering.setOfferingName(request.getOfferingName());
        offering.setBatchType(request.getBatchType());
        offering.setCourse(course);
        offering.setTeacher(teacher);

        offeringRepository.save(offering);

        // STEP 3: DELETE OLD SESSIONS
        sessionRepository.deleteAll(offering.getSessions());
        offering.getSessions().clear();

        // STEP 4: ADD NEW SESSIONS
        List<Session> newSessions = new ArrayList<>();

        for (SessionRequestDTO s : request.getSessions()) {

            Instant startUtc = ZonedDateTime.of(s.getStartTime(), teacherZone).toInstant();
            Instant endUtc = ZonedDateTime.of(s.getEndTime(), teacherZone).toInstant();

            Session session = Session.builder()
                    .offering(offering)
                    .startTimeUtc(startUtc)
                    .endTimeUtc(endUtc)
                    .status(SessionStatus.SCHEDULED)
                    .build();

            newSessions.add(sessionRepository.save(session));
        }

        offering.setSessions(newSessions);

        return offeringMapper.mapToDTO(offering, teacher.getTimezone());
    }

    @Override
    @Transactional
    public void deleteOffering(Long id) {

        Offering offering = offeringRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offering not found"));

        // STEP 1: CHECK IF BOOKING EXISTS
        boolean hasBookings = bookingRepository.existsByOfferingId(offering.getId());

        if (hasBookings) {
            throw new IllegalStateException(
                    "Cannot delete offering. It is already booked by parents."
            );
        }

        // STEP 2: DELETE SESSIONS FIRST
        sessionRepository.deleteAll(offering.getSessions());

        // STEP 3: DELETE OFFERING
        offeringRepository.delete(offering);
    }

    // ======================================================
    // DTO MAPPER
    // ======================================================
//    private OfferingResponseDTO mapToDTO(Offering offering, String timeZone) {
//
//        List<SessionResponseDTO> sessionDTOs = offering.getSessions()
//                .stream()
//                .map(s -> SessionResponseDTO.builder()
//                        .id(s.getId())
//                        .startTimeLocal(TimezoneUtil.convertUtcToLocal(s.getStartTimeUtc(), timeZone))
//                        .endTimeLocal(TimezoneUtil.convertUtcToLocal(s.getEndTimeUtc(), timeZone))
//                        .build())
//                .collect(Collectors.toList());
//
//        return OfferingResponseDTO.builder()
//                .id(offering.getId())
//                .offeringName(offering.getOfferingName())
//                .batchType(offering.getBatchType())
//                .courseId(offering.getCourse().getId())
//                .teacherId(offering.getTeacher().getId())
//                .sessions(sessionDTOs)
//                .build();
//    }

    // ======================================================
    // HELPER CLASS
    // ======================================================
    private static class TimeRange {
        Instant start;
        Instant end;

        TimeRange(Instant start, Instant end) {
            this.start = start;
            this.end = end;
        }
    }



}