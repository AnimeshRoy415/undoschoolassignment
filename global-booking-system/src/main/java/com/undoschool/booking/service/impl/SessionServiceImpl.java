package com.undoschool.booking.service.impl;

import com.undoschool.booking.dto.response.SessionResponseDTO;
import com.undoschool.booking.entity.Session;
import com.undoschool.booking.mapper.SessionMapper;
import com.undoschool.booking.repository.SessionRepository;
import com.undoschool.booking.service.SessionService;
import com.undoschool.booking.util.TimezoneUtil;
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

    @Autowired
    private SessionMapper sessionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SessionResponseDTO> getSessionsByOffering(Long offeringId, String timeZone) {

        List<Session> sessions = sessionRepository.findByOfferingId(offeringId);

        return sessions.stream()
                .map(session -> sessionMapper.toDto(session, timeZone))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SessionResponseDTO getSessionById(Long id, String timeZone) {

        Session session = sessionRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Session not found with id: " + id));

        return sessionMapper.toDto(session, timeZone);
    }
}