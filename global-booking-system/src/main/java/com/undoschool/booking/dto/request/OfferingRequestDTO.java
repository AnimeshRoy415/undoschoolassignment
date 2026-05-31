package com.undoschool.booking.dto.request;

import com.undoschool.booking.dto.request.SessionRequestDTO;
import lombok.Data;

import java.util.List;

@Data
public class OfferingRequestDTO {

    private String offeringName;
    private String batchType;

    private Long courseId;
    private Long teacherId;

    private List<SessionRequestDTO> sessions;
}