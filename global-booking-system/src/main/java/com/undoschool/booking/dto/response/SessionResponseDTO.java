package com.undoschool.booking.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class SessionResponseDTO {

    private Long id;
    private Long offeringId;
//    private Instant startTimeUtc;
//    private Instant endTimeUtc;
    private String startTimeLocal;
    private String endTimeLocal;
    private String timezone;

}