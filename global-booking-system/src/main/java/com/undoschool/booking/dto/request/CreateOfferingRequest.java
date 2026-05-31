package com.undoschool.booking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOfferingRequest {

    @NotNull
    private Long courseId;

    @NotBlank
    private String offeringName;

    private String batchType;
}