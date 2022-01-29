package com.norriszhang.lisa.service;

import com.norriszhang.lisa.datamodel.User;
import com.norriszhang.lisa.dto.LoginUserDto;
import org.springframework.security.core.userdetails.UserDetails;
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
    public Optional<UserDetails> findByToken(String token) {
//        Optional<User> userOptional = Optional.of(tokens.verify(token)).map(map -> map.get("username")).flatMap(userService::findByLoginId);
//        if (!userOptional.isPresent()) {
//            return Optional.empty();
//        }
//        User user = userOptional.get();
//        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getLoginId())
//                .password(user.getPassword())
//                .passwordEncoder(p -> p)
//                .roles(user.getRole())
//                .build();
//        return Optional.of(userDetails);
        return Optional.ofNullable(tokens.verify(token)).map(map -> {
            return org.springframework.security.core.userdetails.User
                .withUsername(map.get("username"))
                .password("fake-password")
                .roles(map.get("role"))
                .build();
        });
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
                              .token(tokens.expiring(Map.of("id", user.getId().toString(), "username", user.getLoginId(), "role", user.getRole())))
                              .build());
    }

    @Override
    public void logout(User user) {
        // Nothing to do.
    }
}
