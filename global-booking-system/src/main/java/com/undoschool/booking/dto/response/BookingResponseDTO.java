package com.undoschool.booking.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponseDTO {

    private Long bookingId;

    private Long parentId;

    private Long offeringId;

    private String status;

    private String message;
}