package com.norriszhang.lisa.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserDto {
    private Long id;
    private String loginUsername;
    private String displayName;
    private String role;
    private String token;
}
