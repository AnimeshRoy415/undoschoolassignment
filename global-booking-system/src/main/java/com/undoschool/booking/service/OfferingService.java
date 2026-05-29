package com.undoschool.booking.service;

import com.undoschool.booking.dto.request.OfferingRequestDto;
import com.undoschool.booking.entity.Offering;

import java.util.List;

public interface OfferingService {

    Offering addOffering(OfferingRequestDto dto);

    List<Offering> getAllOfferings();

    Offering getOfferingById(Long id);

    Offering updateOffering(Long id, OfferingRequestDto dto);

    void deleteOffering(Long id);
}
