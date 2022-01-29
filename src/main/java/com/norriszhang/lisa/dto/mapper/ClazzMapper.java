package com.norriszhang.lisa.dto.mapper;

import com.norriszhang.lisa.datamodel.Clazz;
import com.norriszhang.lisa.dto.ClassesDto.ClassDto;

public class ClazzMapper {
    public static ClassDto map(Clazz clazz) {
        return ClassDto.builder()
            .id(clazz.getId())
            .name(clazz.getName())
            .description(clazz.getDescription())
            .dayOfWeek(clazz.getDayOfWeek())
            .duration(clazz.getDuration())
            .startTime(clazz.getStartTime())
            .build();
    }
}
