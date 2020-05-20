package org.smuslanov.hello.test.service;

import org.smuslanov.hello.test.domain.LoginDto;
import org.smuslanov.hello.test.domain.User;
import org.smuslanov.hello.test.dto.LoginResponseDto;
import org.smuslanov.hello.test.dto.RegisterDto;

public interface AuthService {
    LoginResponseDto login(LoginDto loginDto);

    User register(RegisterDto registerDto);
}