package com.undoschool.booking.service.impl;

import com.undoschool.booking.dto.*;
import com.undoschool.booking.dto.request.OfferingRequestDTO;
import com.undoschool.booking.dto.response.OfferingResponseDTO;
import com.undoschool.booking.dto.response.SessionResponseDTO;
import com.undoschool.booking.entity.*;
import com.undoschool.booking.enums.OfferingStatus;
import com.undoschool.booking.enums.SessionStatus;
import com.undoschool.booking.repository.*;
import com.undoschool.booking.service.OfferingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
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

    @Override
    @Transactional
    public OfferingResponseDTO createOffering(OfferingRequestDTO request) {

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        Offering offering = Offering.builder()
                .offeringName(request.getOfferingName())
                .batchType(request.getBatchType())
                .status(OfferingStatus.ACTIVE)
                .course(course)
                .teacher(teacher)
                .teacherTimezone(teacher.getTimezone())
                .build();

        Offering savedOffering = offeringRepository.save(offering);

        // Convert and save sessions
        ZoneId teacherZone = ZoneId.of(teacher.getTimezone());

        List<Session> sessions = request.getSessions().stream().map(s -> {

            Instant startUtc = ZonedDateTime.of(s.getStartTime(), teacherZone)
                    .toInstant();

            Instant endUtc = ZonedDateTime.of(s.getEndTime(), teacherZone)
                    .toInstant();

            Session session = Session.builder()
                    .offering(savedOffering)
                    .startTimeUtc(startUtc)
                    .endTimeUtc(endUtc)
                    .status(SessionStatus.SCHEDULED)
                    .build();

            return sessionRepository.save(session);

        }).collect(Collectors.toList());

        savedOffering.setSessions(sessions);

        return mapToDTO(savedOffering);
    }

    @Override
    public OfferingResponseDTO getOfferingById(Long id) {

        Offering offering = offeringRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offering not found"));

        return mapToDTO(offering);
    }

    @Override
    public List<OfferingResponseDTO> getAllOfferings() {

        return offeringRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private OfferingResponseDTO mapToDTO(Offering offering) {

        List<SessionResponseDTO> sessionDTOs = offering.getSessions()
                .stream()
                .map(s -> SessionResponseDTO.builder()
                        .id(s.getId())
                        .startTimeUtc(s.getStartTimeUtc())
                        .endTimeUtc(s.getEndTimeUtc())
                        .build())
                .collect(Collectors.toList());

        return OfferingResponseDTO.builder()
                .id(offering.getId())
                .offeringName(offering.getOfferingName())
                .batchType(offering.getBatchType())
                .courseId(offering.getCourse().getId())
                .teacherId(offering.getTeacher().getId())
                .sessions(sessionDTOs)
                .build();
    }
}