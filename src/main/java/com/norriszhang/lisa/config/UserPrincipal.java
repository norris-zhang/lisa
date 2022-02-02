package com.norriszhang.lisa.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal extends User {
    private final Long id;
    private final String displayName;
    private final String token;

    public UserPrincipal(UserDetails user, Long id, String displayName, String token) {
        super(user.getUsername(), user.getPassword(), user.getAuthorities());
        this.id = id;
        this.displayName = displayName;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getToken() {
        return token;
    }
}
