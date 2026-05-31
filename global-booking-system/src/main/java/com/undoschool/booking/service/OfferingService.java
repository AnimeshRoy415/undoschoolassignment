package com.undoschool.booking.service;



import com.undoschool.booking.dto.request.OfferingRequestDTO;
import com.undoschool.booking.dto.response.OfferingResponseDTO;

import java.util.List;

public interface OfferingService {

    OfferingResponseDTO createOffering(OfferingRequestDTO request);

    OfferingResponseDTO getOfferingById(Long id, String timeZone);

    List<OfferingResponseDTO> getAllOfferings(String timeZone);

    OfferingResponseDTO updateOffering(Long id, OfferingRequestDTO request);

    void deleteOffering(Long id);
}