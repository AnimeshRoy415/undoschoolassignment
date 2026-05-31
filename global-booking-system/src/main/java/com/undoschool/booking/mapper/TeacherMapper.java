package com.undoschool.booking.mapper;

import com.undoschool.booking.dto.TeacherResponseDTO;
import com.undoschool.booking.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

    public static TeacherResponseDTO toDto(Teacher teacher) {

        return TeacherResponseDTO.builder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .email(teacher.getEmail())
                .phoneNumber(teacher.getPhoneNumber())
                .timezone(teacher.getTimezone())
                .build();
    }

}