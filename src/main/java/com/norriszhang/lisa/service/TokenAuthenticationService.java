package com.norriszhang.lisa.service;

import com.norriszhang.lisa.datamodel.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
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
        Optional<User> userOptional = Optional.of(tokens.verify(token)).map(map -> map.get("username")).flatMap(userService::findByLoginId);
        if (!userOptional.isPresent()) {
            return Optional.empty();
        }
        User user = userOptional.get();
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getLoginId())
                .password(user.getPassword())
                .passwordEncoder(p -> p)
                .roles(user.getRole())
                .build();
        return Optional.of(userDetails);
    }

    @Override
    public Optional<String> login(String username, String password) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return userService.findByLoginId(username)
                          .filter(user -> encoder.matches(password, user.getPassword()))
                          .map(user -> tokens.expiring(Map.of("username", user.getLoginId())));
    }

    @Override
    public void logout(User user) {
        // Nothing to do.
    }
}
