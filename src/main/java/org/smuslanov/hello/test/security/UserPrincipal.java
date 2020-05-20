package org.smuslanov.hello.test.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class UserPrincipal extends org.springframework.security.core.userdetails.User {
    private final org.smuslanov.hello.test.domain.User user;

    public UserPrincipal(org.smuslanov.hello.test.domain.User user, boolean enabled, boolean accountNonExpired,
                         boolean credentialsNonExpired,
                         boolean accountNonLocked,
                         Collection<? extends GrantedAuthority> authorities) {
        super(user.getName(),
                user.getPassword(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities);
        this.user = user;
    }
}