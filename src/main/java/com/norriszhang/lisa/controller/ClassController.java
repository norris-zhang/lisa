package com.norriszhang.lisa.controller;

import com.norriszhang.lisa.datamodel.Clazz;
import com.norriszhang.lisa.service.ClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClassController {
    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/classes")
    public ResponseEntity<List<Clazz>> listClasses() {
        return ResponseEntity.ok(classService.listClasses());
    }

//    public ResponseEntity<List<Student>>
}
