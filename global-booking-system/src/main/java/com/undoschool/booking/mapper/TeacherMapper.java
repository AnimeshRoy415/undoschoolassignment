package com.undoschool.booking.mapper;

import com.undoschool.booking.dto.request.TeacherRequestDto;
import com.undoschool.booking.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

    public Teacher toEntity(TeacherRequestDto dto) {
        if (dto == null) return null;

        Teacher teacher = new Teacher();

        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setEmail(dto.getEmail());
        teacher.setTimezone(dto.getTimezone());
        teacher.setCountry(dto.getCountry());
        teacher.setExpertise(dto.getExpertise());

        return teacher;
    }
}