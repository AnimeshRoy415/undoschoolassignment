package com.undoschool.booking.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OfferingResponseDTO {

    private Long id;
    private String offeringName;
    private String batchType;

    private Long courseId;
    private Long teacherId;

    private List<SessionResponseDTO> sessions;
}