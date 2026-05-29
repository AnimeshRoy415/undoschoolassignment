package com.undoschool.booking.service;

import com.undoschool.booking.entity.Parent;

import java.util.List;

public interface ParentService {


    Parent saveParent(Parent parent);

    List<Parent> getAllParents();

    Parent getParentById(Long id);

    Parent updateParent(Long id, Parent parent);

    void deleteParent(Long id);
}
