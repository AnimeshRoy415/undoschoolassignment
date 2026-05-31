package com.undoschool.booking.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParentBookingResponse {

    private Long bookingId;

    private String courseName;

    private String offeringName;

    private String bookingStatus;

    private List<SessionResponseDTO> sessions;
}