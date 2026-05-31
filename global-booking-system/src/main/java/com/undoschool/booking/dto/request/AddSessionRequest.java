package com.undoschool.booking.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddSessionRequest {

    @NotNull
    @Schema(example = "2026-06-06T10:00:00Z")
    private Instant startTime;

    @NotNull
    @Schema(example = "2026-06-06T11:00:00Z")
    private Instant endTime;

    public void validate() {
        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new IllegalArgumentException("Invalid session time range");
        }
    }
}