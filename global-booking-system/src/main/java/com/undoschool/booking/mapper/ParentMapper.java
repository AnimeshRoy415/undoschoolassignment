package com.undoschool.booking.mapper;

import com.undoschool.booking.dto.response.ParentResponseDTO;
import com.undoschool.booking.entity.Parent;
import org.springframework.stereotype.Component;

@Component
public class ParentMapper {

    public static ParentResponseDTO toDto(Parent parent) {

        return ParentResponseDTO.builder()
                .id(parent.getId())
                .firstName(parent.getFirstName())
                .lastName(parent.getLastName())
                .email(parent.getEmail())
                .timezone(parent.getTimezone())
                .country(parent.getCountry())
                .phoneNumber(parent.getPhoneNumber())
                .build();
    }
}