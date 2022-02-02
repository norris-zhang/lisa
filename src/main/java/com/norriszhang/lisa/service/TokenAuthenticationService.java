package com.norriszhang.lisa.service;

import com.norriszhang.lisa.config.UserPrincipal;
import com.norriszhang.lisa.datamodel.User;
import com.norriszhang.lisa.dto.LoginUserDto;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class TokenAuthenticationService implements UserAuthenticationService {
    private final TokenService tokens;
    private final UserService userService;


    public TokenAuthenticationService(TokenService tokens, UserService userService) {
        this.tokens = tokens;
        this.userService = userService;
    }

    @Override
    public Optional<UserPrincipal> findByToken(String token) {
        return Optional.ofNullable(tokens.verify(token)).map(map -> new UserPrincipal(org.springframework.security.core.userdetails.User
            .withUsername(map.get("username"))
            .password("fake-password")
            .roles(map.get("role"))
            .build(), Long.valueOf(map.get("id")), map.get("displayName"), token));
    }

    @Override
    public Optional<LoginUserDto> login(String username, String password) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return userService.findByLoginId(username)
                          .filter(user -> encoder.matches(password, user.getPassword()))
                          .map(user -> LoginUserDto.builder()
                              .id(user.getId())
                              .loginUsername(user.getLoginId())
                              .displayName(user.getDisplayName())
                              .role(user.getRole())
                              .token(tokens.expiring(Map.of("id", user.getId().toString(), "username", user.getLoginId(), "role", user.getRole(), "displayName", user.getDisplayName())))
                              .build());
    }

    @Override
    public void logout(User user) {
        // Nothing to do.
    }
}
