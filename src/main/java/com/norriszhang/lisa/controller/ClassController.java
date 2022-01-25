package com.norriszhang.lisa.controller;

import com.norriszhang.lisa.datamodel.Clazz;
import com.norriszhang.lisa.datamodel.Student;
import com.norriszhang.lisa.service.ClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
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
    public ResponseEntity<List<Student>> listStudents(@PathVariable(value = "classId", required = true) Long classId) {
        
        return null;
    }
}
