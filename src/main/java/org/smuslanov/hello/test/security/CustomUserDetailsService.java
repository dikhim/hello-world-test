package org.smuslanov.hello.test.security;

import lombok.extern.slf4j.Slf4j;
import org.smuslanov.hello.test.domain.User;
import org.smuslanov.hello.test.exception.ResourceNotFoundException;
import org.smuslanov.hello.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        log.info("load user by username '{}'", name);

        User user = userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User with specified name is not found:"+ name));
        log.info("create principal for user id '{}'", user.getId());
        UserPrincipal principal = createUserPrincipal(user);
        return principal;
    }

    @Transactional
    public UserPrincipal loadUserPrincipalById(Long id) {
        User user = loadUserById(id);

        return createUserPrincipal(user);
    }

    public User loadUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }


    private UserPrincipal createUserPrincipal(User User) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNotExpired = true;
        boolean accountNonLocked = true;
        return new UserPrincipal(
                User,
                enabled,
                accountNonExpired,
                credentialsNotExpired,
                accountNonLocked,
                new ArrayList<>()
        );
    }
}