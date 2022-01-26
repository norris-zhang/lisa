package com.norriszhang.lisa.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class StudentsDto {
    @Singular
    private List<StudentDto> students;
    @Data
    @Builder
    public static class StudentDto {
        private String name;
    }
}
