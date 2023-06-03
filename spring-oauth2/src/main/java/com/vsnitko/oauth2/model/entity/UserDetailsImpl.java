package com.vsnitko.oauth2.model.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * User entity which is used in Spring Security. Formally, it's an extension of {@link User} class. This class was
 * introduced not to mess up fields from Spring Security with application-business fields
 *
 * @author v.snitko
 * @since 2022.10.15
 */
@Getter
@AllArgsConstructor
@Builder
public class UserDetailsImpl implements OAuth2User, UserDetails {

    private final transient User user;

    private final HashMap<String, Object> attributes = new HashMap<>();

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return user.getEmail();
    }
}
