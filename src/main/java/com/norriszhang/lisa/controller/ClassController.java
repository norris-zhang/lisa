package com.norriszhang.lisa.controller;

import com.norriszhang.lisa.datamodel.Clazz;
import com.norriszhang.lisa.dto.ClassesDto;
import com.norriszhang.lisa.dto.ClassesDto.ClassDto;
import com.norriszhang.lisa.dto.StudentsDto;
import com.norriszhang.lisa.dto.StudentsDto.StudentDto;
import com.norriszhang.lisa.dto.mapper.ClazzMapper;
import com.norriszhang.lisa.service.ClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.norriszhang.lisa.dto.mapper.ClazzMapper.map;

@RestController
public class ClassController {
    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/classes")
    public ResponseEntity<ClassesDto> listClasses() {
        return ResponseEntity.ok(classService.listClasses().stream()
            .map(clazz -> map(clazz))
            .reduce(ClassesDto.builder().build(),
                (classesDto, classDto) -> classesDto.toBuilder().clazz(classDto).build(),
                (accum1, accum2) -> {
                    accum2.getClasses().forEach(c -> accum1.getClasses().add(c));
                    return accum1;
                }));
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
