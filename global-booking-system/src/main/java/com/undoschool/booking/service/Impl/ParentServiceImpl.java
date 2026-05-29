package com.undoschool.booking.service.Impl;

import com.undoschool.booking.entity.Parent;
import com.undoschool.booking.repository.ParentRepository;
import com.undoschool.booking.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ParentRepository parentRepository;

    @Override
    public Parent saveParent(Parent parent) {
        return parentRepository.save(parent);
    }
    @Override
    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }
    @Override
    public Parent getParentById(Long id) {
        return parentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parent not found"));
    }
    @Override
    public Parent updateParent(Long id, Parent updatedParent) {

        Parent existingParent = getParentById(id);

        existingParent.setFirstName(updatedParent.getFirstName());
        existingParent.setLastName(updatedParent.getLastName());
        existingParent.setEmail(updatedParent.getEmail());
        existingParent.setPhoneNumber(updatedParent.getPhoneNumber());
        existingParent.setCountry(updatedParent.getCountry());
        existingParent.setTimezone(updatedParent.getTimezone());

        return parentRepository.save(existingParent);
    }

    @Override
    public void deleteParent(Long id) {

        Parent parent = getParentById(id);

        parentRepository.delete(parent);
    }
}