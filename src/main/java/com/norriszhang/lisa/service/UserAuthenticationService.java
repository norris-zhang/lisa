package com.norriszhang.lisa.service;

import com.norriszhang.lisa.config.UserPrincipal;
import com.norriszhang.lisa.datamodel.User;
import com.norriszhang.lisa.dto.LoginUserDto;

import java.util.Optional;

public interface UserAuthenticationService {
    Optional<UserPrincipal> findByToken(String token);
    Optional<LoginUserDto> login(String username, String password);
    void logout(User user);
}
