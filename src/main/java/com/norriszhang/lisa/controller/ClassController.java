package com.norriszhang.lisa.controller;

import com.norriszhang.lisa.datamodel.Clazz;
import com.norriszhang.lisa.dto.StudentsDto;
import com.norriszhang.lisa.dto.StudentsDto.StudentDto;
import com.norriszhang.lisa.service.ClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @GetMapping("/students/{classId}")
    public ResponseEntity<StudentsDto> listStudents(@PathVariable(value = "classId", required = true) Long classId) {
        StudentsDto students = StudentsDto.builder()
            .student(StudentDto.builder()
                .name("Dongchen Zhang")
                .build())
            .build();
        return ResponseEntity.ok(students);
    }
}
