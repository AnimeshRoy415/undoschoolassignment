package com.undoschool.booking.service.impl;


import com.undoschool.booking.dto.request.ParentRequestDTO;
import com.undoschool.booking.dto.response.ParentResponseDTO;
import com.undoschool.booking.entity.Parent;
import com.undoschool.booking.repository.ParentRepository;
import com.undoschool.booking.service.ParentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ParentRepository parentRepository;

    @Override
    public ParentResponseDTO createParent(ParentRequestDTO request) {

        validateTimezone(request.getTimezone());

        Parent parent = Parent.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .timezone(request.getTimezone())
                .country(request.getCountry())
                .phoneNumber(request.getPhoneNumber())
                .build();

        Parent saved = parentRepository.save(parent);

        return mapToDTO(saved);
    }

    @Override
    public ParentResponseDTO getParentById(Long id) {

        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parent not found with id: " + id));

        return mapToDTO(parent);
    }

    @Override
    public List<ParentResponseDTO> getAllParents() {

        return parentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ParentResponseDTO updateParent(Long id, ParentRequestDTO request) {

        validateTimezone(request.getTimezone());

        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parent not found with id: " + id));

        parent.setFirstName(request.getFirstName());
        parent.setLastName(request.getLastName());
        parent.setEmail(request.getEmail());
        parent.setTimezone(request.getTimezone());
        parent.setCountry(request.getCountry());
        parent.setPhoneNumber(request.getPhoneNumber());

        Parent updated = parentRepository.save(parent);

        return mapToDTO(updated);
    }

    @Override
    public void deleteParent(Long id) {

        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parent not found with id: " + id));

        parentRepository.delete(parent);
    }

    private ParentResponseDTO mapToDTO(Parent parent) {
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

    private void validateTimezone(String timezone) {
        try {
            ZoneId.of(timezone);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid timezone: " + timezone);
        }
    }
}