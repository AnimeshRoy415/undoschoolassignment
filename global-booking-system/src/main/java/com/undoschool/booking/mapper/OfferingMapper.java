package com.undoschool.booking.mapper;

import com.undoschool.booking.dto.request.OfferingRequestDTO;
import com.undoschool.booking.dto.request.OfferingRequestDTO;
import com.undoschool.booking.entity.Course;
import com.undoschool.booking.entity.Offering;
import com.undoschool.booking.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class OfferingMapper {

    public Offering toEntity(
            OfferingRequestDTO dto,
            Teacher teacher,
            Course course
    ) {
        if (dto == null) return null;

        Offering offering = new Offering();

        offering.setTeacher(teacher);
        offering.setCourse(course);

        return offering;
    }
}