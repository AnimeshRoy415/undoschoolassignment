package com.undoschool.booking.service;


import com.undoschool.booking.dto.response.SessionResponseDTO;

import java.util.List;

public interface SessionService {

    List<SessionResponseDTO> getSessionsByOffering(Long offeringId, String timeZone);

    SessionResponseDTO getSessionById(Long id, String timeZone);
}