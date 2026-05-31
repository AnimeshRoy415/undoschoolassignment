package com.undoschool.booking.mapper;

import com.undoschool.booking.dto.response.OfferingResponseDTO;
import com.undoschool.booking.dto.response.SessionResponseDTO;
import com.undoschool.booking.entity.Offering;
import com.undoschool.booking.util.TimezoneUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OfferingMapper {



    public OfferingResponseDTO mapToDTO(Offering offering, String timeZone) {

        List<SessionResponseDTO> sessionDTOs = offering.getSessions()
                .stream()
                .map(s -> SessionResponseDTO.builder()
                        .id(s.getId())
                        .startTimeLocal(TimezoneUtil.convertUtcToLocal(s.getStartTimeUtc(), timeZone))
                        .endTimeLocal(TimezoneUtil.convertUtcToLocal(s.getEndTimeUtc(), timeZone))
                        .build())
                .collect(Collectors.toList());

        return OfferingResponseDTO.builder()
                .id(offering.getId())
                .offeringName(offering.getOfferingName())
                .batchType(offering.getBatchType())
                .courseId(offering.getCourse().getId())
                .teacherId(offering.getTeacher().getId())
                .sessions(sessionDTOs)
                .build();
    }
}
