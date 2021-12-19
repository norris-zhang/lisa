package com.norriszhang.lisa.service;

import com.norriszhang.lisa.datamodel.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserAuthenticationService {
    Optional<UserDetails> findByToken(String token);
    Optional<String> login(String username, String password);
    void logout(User user);
}
