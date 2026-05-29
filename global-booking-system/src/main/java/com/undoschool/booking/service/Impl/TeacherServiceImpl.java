package com.undoschool.booking.service.Impl;

import com.undoschool.booking.dto.request.TeacherRequestDto;
import com.undoschool.booking.entity.Teacher;
import com.undoschool.booking.mapper.TeacherMapper;
import com.undoschool.booking.repository.TeacherRepository;
import com.undoschool.booking.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher addTeacher(TeacherRequestDto dto) {
        Teacher teacher = teacherMapper.toEntity(dto);
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    @Override
    public Teacher updateTeacher(Long id, TeacherRequestDto dto) {

        Teacher existingTeacher = getTeacherById(id);

        existingTeacher.setFirstName(dto.getFirstName());
        existingTeacher.setLastName(dto.getLastName());
        existingTeacher.setEmail(dto.getEmail());
        existingTeacher.setCountry(dto.getCountry());
        existingTeacher.setTimezone(dto.getTimezone());
        existingTeacher.setExpertise(dto.getExpertise());

        return teacherRepository.save(existingTeacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        Teacher teacher = getTeacherById(id);
        teacherRepository.delete(teacher);
    }
}