package com.vsnitko.oauth2.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

/**
 * @author v.snitko
 * @since 2022.10.15
 */
@Getter
@AllArgsConstructor
@Builder
public class UserDetailsImpl implements OAuth2User, UserDetails {

    private String username;

    private String password;

    private List<SimpleGrantedAuthority> authorities;

    //related user
    private final User user;

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
        return null;
    }

    @Override
    public String getName() {
        return username;
    }
}
