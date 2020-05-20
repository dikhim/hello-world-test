package org.smuslanov.hello.test.domain;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class LoginDto {
    @NotNull(message = "name should not be null")
    private final String name;

    @NotNull(message = "password should not be null")
    @Size(min = 6, max = 20, message = "password size should be between 6 and 20")
    private final String password;
}