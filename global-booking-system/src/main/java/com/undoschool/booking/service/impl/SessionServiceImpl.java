package com.undoschool.booking.service.impl;

import com.undoschool.booking.dto.response.SessionResponseDTO;
import com.undoschool.booking.entity.Session;
import com.undoschool.booking.repository.SessionRepository;
import com.undoschool.booking.service.SessionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    /**
     * Get all sessions for a given offering
     * SAFE: uses transaction to avoid LazyInitializationException
     */
    @Override
    @Transactional(readOnly = true)
    public List<SessionResponseDTO> getSessionsByOffering(Long offeringId) {

        List<Session> sessions = sessionRepository.findByOfferingId(offeringId);

        return sessions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get single session by id
     */
    @Override
    @Transactional(readOnly = true)
    public SessionResponseDTO getSessionById(Long id) {

        Session session = sessionRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Session not found with id: " + id));

        return mapToDTO(session);
    }

    /**
     * DTO mapper (IMPORTANT FIX FOR offeringId ISSUE)
     */
    private SessionResponseDTO mapToDTO(Session session) {

        return SessionResponseDTO.builder()
                .id(session.getId())
                .offeringId(session.getOffering().getId())  // ⭐ FIXED LINE
                .startTimeUtc(session.getStartTimeUtc())
                .endTimeUtc(session.getEndTimeUtc())
                .build();
    }
}