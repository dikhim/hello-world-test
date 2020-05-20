package org.smuslanov.hello.test.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.smuslanov.hello.test.domain.LoginDto;
import org.smuslanov.hello.test.domain.User;
import org.smuslanov.hello.test.dto.LoginResponseDto;
import org.smuslanov.hello.test.dto.RegisterDto;
import org.smuslanov.hello.test.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Api(
        value = "/auth",
        tags = "auth")
@RestController
@RequestMapping("${api.path}/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ApiOperation(
            value = "Authenticate User",
            tags = "auth"
    )
    @PostMapping("/login")
    public LoginResponseDto login(@Valid @RequestBody LoginDto loginDto) {
        log.info("logging in with the name {}", loginDto.getName());
        return authService.login(loginDto);
    }

    @ApiOperation(value = "Register new User",
            tags = "auth")
    @PostMapping("/register")
    public User registerUser(@Valid @RequestBody RegisterDto registerDto) {
        return authService.register(registerDto);
    }
}