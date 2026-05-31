package com.undoschool.booking.service.impl;


import com.undoschool.booking.dto.request.ParentRequestDTO;
import com.undoschool.booking.dto.response.ParentResponseDTO;
import com.undoschool.booking.entity.Parent;
import com.undoschool.booking.mapper.ParentMapper;
import com.undoschool.booking.repository.ParentRepository;
import com.undoschool.booking.service.ParentService;
import com.undoschool.booking.util.TimezoneUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ParentRepository parentRepository;

    @Override
    public ParentResponseDTO createParent(ParentRequestDTO request) {

        TimezoneUtil.validateTimezone(request.getTimezone());

        Parent parent = Parent.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .timezone(request.getTimezone())
                .country(request.getCountry())
                .phoneNumber(request.getPhoneNumber())
                .build();

        Parent saved = parentRepository.save(parent);

        return ParentMapper.toDto(saved);
    }

    @Override
    public ParentResponseDTO getParentById(Long id) {

        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parent not found with id: " + id));

        return ParentMapper.toDto(parent);
    }

    @Override
    public List<ParentResponseDTO> getAllParents() {

        return parentRepository.findAll()
                .stream()
                .map(ParentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParentResponseDTO updateParent(Long id, ParentRequestDTO request) {

        TimezoneUtil.validateTimezone(request.getTimezone());

        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parent not found with id: " + id));

        parent.setFirstName(request.getFirstName());
        parent.setLastName(request.getLastName());
        parent.setEmail(request.getEmail());
        parent.setTimezone(request.getTimezone());
        parent.setCountry(request.getCountry());
        parent.setPhoneNumber(request.getPhoneNumber());

        Parent updated = parentRepository.save(parent);

        return ParentMapper.toDto(updated);
    }

    @Override
    public void deleteParent(Long id) {

        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parent not found with id: " + id));

        parentRepository.delete(parent);
    }
}