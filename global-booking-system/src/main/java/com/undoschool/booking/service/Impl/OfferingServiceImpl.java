package com.undoschool.booking.service.Impl;

import com.undoschool.booking.dto.request.OfferingRequestDto;
import com.undoschool.booking.entity.Course;
import com.undoschool.booking.entity.Offering;
import com.undoschool.booking.entity.Teacher;
import com.undoschool.booking.mapper.OfferingMapper;
import com.undoschool.booking.repository.CourseRepository;
import com.undoschool.booking.repository.OfferingRepository;
import com.undoschool.booking.repository.TeacherRepository;
import com.undoschool.booking.service.OfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferingServiceImpl implements OfferingService {

    @Autowired
    private OfferingRepository offeringRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private OfferingMapper offeringMapper;

    @Override
    public Offering addOffering(OfferingRequestDto dto) {

        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + dto.getTeacherId()));

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + dto.getCourseId()));

        Offering offering = offeringMapper.toEntity(dto, teacher, course);

        return offeringRepository.save(offering);
    }

    @Override
    public List<Offering> getAllOfferings() {
        return offeringRepository.findAll();
    }

    @Override
    public Offering getOfferingById(Long id) {
        return offeringRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offering not found with id: " + id));
    }

    @Override
    public Offering updateOffering(Long id, OfferingRequestDto dto) {

        Offering existing = getOfferingById(id);

        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        existing.setTitle(dto.getTitle());
        existing.setMaxStudents(dto.getMaxStudents());
        existing.setEnrolledStudents(dto.getEnrolledStudents());
        existing.setActive(dto.getActive());

        existing.setTeacher(teacher);
        existing.setCourse(course);

        return offeringRepository.save(existing);
    }

    @Override
    public void deleteOffering(Long id) {
        Offering offering = getOfferingById(id);
        offeringRepository.delete(offering);
    }
}