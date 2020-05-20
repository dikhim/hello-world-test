package org.smuslanov.hello.test.service;

import lombok.extern.slf4j.Slf4j;
import org.smuslanov.hello.test.domain.LoginDto;
import org.smuslanov.hello.test.domain.User;
import org.smuslanov.hello.test.dto.LoginResponseDto;
import org.smuslanov.hello.test.dto.RegisterDto;
import org.smuslanov.hello.test.exception.BadRequestException;
import org.smuslanov.hello.test.repository.UserRepository;
import org.smuslanov.hello.test.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public LoginResponseDto login(LoginDto loginDto) {
        User user = userRepository.findByName(loginDto.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User with the specified username and password not found"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getName(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("user id={} logged in", user.getId());
        String jwt = tokenProvider.generateToken(authentication);


        return new LoginResponseDto(user.getId(), user.getName(), jwt);
    }

    @Override
    public User register(RegisterDto registerDto) {
        if (userRepository.existsByName(registerDto.getName()))
            throw new BadRequestException("Email Address already in use");
        User user = new User();

        user.setName(registerDto.getName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user = userRepository.save(user);

        return user;
    }
}