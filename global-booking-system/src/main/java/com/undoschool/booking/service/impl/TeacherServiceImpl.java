package com.undoschool.booking.service.impl;

import com.undoschool.booking.dto.TeacherRequestDTO;
import com.undoschool.booking.dto.TeacherResponseDTO;
import com.undoschool.booking.entity.Teacher;
import com.undoschool.booking.mapper.TeacherMapper;
import com.undoschool.booking.repository.TeacherRepository;
import com.undoschool.booking.service.TeacherService;
import com.undoschool.booking.util.TimezoneUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public TeacherResponseDTO createTeacher(TeacherRequestDTO request) {

        TimezoneUtil.validateTimezone(request.getTimezone());

        Teacher teacher = Teacher.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .timezone(request.getTimezone())
                .build();

        Teacher saved = teacherRepository.save(teacher);

        return TeacherMapper.toDto(saved);
    }

    @Override
    public TeacherResponseDTO getTeacherById(Long id) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + id));

        return TeacherMapper.toDto(teacher);
    }

    @Override
    public List<TeacherResponseDTO> getAllTeachers() {

        return teacherRepository.findAll()
                .stream()
                .map(TeacherMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherResponseDTO updateTeacher(Long id, TeacherRequestDTO request) {

        TimezoneUtil.validateTimezone(request.getTimezone());

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + id));

        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmail(request.getEmail());
        teacher.setPhoneNumber(request.getPhoneNumber());
        teacher.setTimezone(request.getTimezone());

        Teacher updated = teacherRepository.save(teacher);

        return TeacherMapper.toDto(updated);
    }

    @Override
    public void deleteTeacher(Long id) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + id));

        teacherRepository.delete(teacher);
    }

}