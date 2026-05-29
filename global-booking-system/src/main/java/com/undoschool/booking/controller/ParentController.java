package com.undoschool.booking.controller;

import com.undoschool.booking.entity.Parent;
import com.undoschool.booking.service.Impl.ParentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
@CrossOrigin("*")
public class ParentController {

    @Autowired
    private ParentServiceImpl parentServiceImpl;

    @PostMapping
    public Parent addParent(@RequestBody Parent parent) {
        return parentServiceImpl.saveParent(parent);
    }

    @GetMapping
    public List<Parent> getAllParents() {
        return parentServiceImpl.getAllParents();
    }

    @GetMapping("/{id}")
    public Parent getParentById(@PathVariable Long id) {
        return parentServiceImpl.getParentById(id);
    }

    @PutMapping("/{id}")
    public Parent updateParent(@PathVariable Long id,
                               @RequestBody Parent parent) {
        return parentServiceImpl.updateParent(id, parent);
    }

    @DeleteMapping("/{id}")
    public String deleteParent(@PathVariable Long id) {
        parentServiceImpl.deleteParent(id);
        return "Parent deleted successfully";
    }
}