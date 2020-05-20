package org.smuslanov.hello.test.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private final Long userId;
    private final String name;
    private final String token;
}