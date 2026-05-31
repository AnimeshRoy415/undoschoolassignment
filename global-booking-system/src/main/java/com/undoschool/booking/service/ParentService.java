package com.undoschool.booking.service;

import com.undoschool.booking.dto.request.ParentRequestDTO;
import com.undoschool.booking.dto.response.ParentResponseDTO;


import java.util.List;

public interface ParentService {

    ParentResponseDTO createParent(ParentRequestDTO request);

    ParentResponseDTO getParentById(Long id);

    List<ParentResponseDTO> getAllParents();

    ParentResponseDTO updateParent(Long id, ParentRequestDTO request);

    void deleteParent(Long id);
}