package org.smuslanov.hello.test.service;

import org.junit.Before;
import org.junit.Test;
import org.smuslanov.hello.test.domain.LoginDto;
import org.smuslanov.hello.test.domain.User;
import org.smuslanov.hello.test.dto.LoginResponseDto;
import org.smuslanov.hello.test.dto.RegisterDto;
import org.smuslanov.hello.test.exception.BadRequestException;
import org.smuslanov.hello.test.repository.UserRepository;
import org.smuslanov.hello.test.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthServiceImpTest {
    private AuthService authService;

    @Before
    public void init() {
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(any())).then(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });
        User user = new User();
        user.setId(1L);
        user.setName("user1");
        user.setPassword("encoded password");
        when(userRepository.findByName("user1")).thenReturn(Optional.of(user));
        when(userRepository.existsByName("user1")).thenReturn(true);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        when(passwordEncoder.encode("password")).thenReturn("encoded password");

        JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
        when(jwtTokenProvider.generateToken(any())).thenReturn("token");
        authService = new AuthServiceImpl(authenticationManager, userRepository, passwordEncoder, jwtTokenProvider);
    }

    @Test
    public void Register_UserRegistered_PassedNameAndPassword() {
        RegisterDto registerDto = new RegisterDto("name", "password");
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setName("name");
        expectedUser.setPassword("encoded password");
        User user = authService.register(registerDto);

        assertEquals(expectedUser, user);
    }

    @Test(expected = BadRequestException.class)
    public void Register_ThrowsException_PassedNameThatAlreadyExists() {
        RegisterDto registerDto = new RegisterDto("user1", "password");
        authService.register(registerDto);
    }

    @Test
    public void Login_UserLoggedIn_PassedRightNameAndPassword() {
        LoginDto loginDto = new LoginDto("user1", "password");

        LoginResponseDto response = authService.login(loginDto);
        LoginResponseDto expectedResponse = new LoginResponseDto(1L, "user1", "token");

        assertEquals(expectedResponse, response);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void Login_ThrowsException_PassedNameThatAlreadyExists() {
        LoginDto loginDto = new LoginDto("doesn't exist", "password");

        authService.login(loginDto);
    }
}

