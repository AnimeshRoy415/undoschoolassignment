package com.undoschool.booking.service;



import com.undoschool.booking.dto.request.OfferingRequestDTO;
import com.undoschool.booking.dto.response.OfferingResponseDTO;

import java.util.List;

public interface OfferingService {

    OfferingResponseDTO createOffering(OfferingRequestDTO request);

    OfferingResponseDTO getOfferingById(Long id);

    List<OfferingResponseDTO> getAllOfferings();
}