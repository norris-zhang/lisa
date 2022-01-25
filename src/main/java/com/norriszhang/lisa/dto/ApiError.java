package com.norriszhang.lisa.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class ApiError {
    @Singular
    private List<String> errorMessages;
}
