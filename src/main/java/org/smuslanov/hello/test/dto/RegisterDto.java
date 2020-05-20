package org.smuslanov.hello.test.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class RegisterDto {
    @NotNull(message = "username should not be null")
    @Size(min = 4, max = 15, message = "username size should be between 4 and 15")
    private final String name;

    @NotNull(message = "password should not be null")
    @Size(min = 6, max = 20, message = "password size should be between 6 and 20")
    private final String password;
}