package com.norriszhang.lisa.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class ClassesDto {
    @Singular("clazz")
    private List<ClassDto> classes;

    @Data
    @Builder
    public static class ClassDto {
        private Long id;
        private String name;
        private String description;
        private String dayOfWeek;
        private LocalTime startTime;
        private Integer duration;
    }
}
