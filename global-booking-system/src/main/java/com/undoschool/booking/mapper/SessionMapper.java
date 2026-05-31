package com.undoschool.booking.mapper;

import com.undoschool.booking.dto.response.SessionResponseDTO;
import com.undoschool.booking.entity.Session;
import com.undoschool.booking.util.TimezoneUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionMapper {


    public SessionResponseDTO toDto(
            Session session,
            String timezone) {

        return SessionResponseDTO.builder()
                .id(session.getId())
                .offeringId(session.getOffering().getId())
                .startTimeLocal(
                        TimezoneUtil.convertUtcToLocal(
                                session.getStartTimeUtc(),
                                timezone))
                .endTimeLocal(
                        TimezoneUtil.convertUtcToLocal(
                                session.getEndTimeUtc(),
                                timezone))
                .timezone(timezone)
                .build();
    }


}