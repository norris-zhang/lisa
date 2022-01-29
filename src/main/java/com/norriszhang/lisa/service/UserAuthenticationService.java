package com.norriszhang.lisa.service;

import com.norriszhang.lisa.datamodel.User;
import com.norriszhang.lisa.dto.LoginUserDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserAuthenticationService {
    Optional<UserDetails> findByToken(String token);
    Optional<LoginUserDto> login(String username, String password);
    void logout(User user);
}
