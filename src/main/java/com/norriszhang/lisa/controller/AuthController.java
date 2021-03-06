package com.norriszhang.lisa.controller;

import com.norriszhang.lisa.config.UserPrincipal;
import com.norriszhang.lisa.datamodel.User;
import com.norriszhang.lisa.dto.LoginUserDto;
import com.norriszhang.lisa.service.UserAuthenticationService;
import com.norriszhang.lisa.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserAuthenticationService userAuthService;
    private final UserService userService;

    public AuthController(UserAuthenticationService userAuthService, UserService userService) {
        this.userAuthService = userAuthService;
        this.userService = userService;
    }

    @GetMapping("/hc")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/checkLogin")
    public ResponseEntity<LoginUserDto> checkLogin(Authentication auth) {
        log.info("checkLogin...");
        UserPrincipal principal = (UserPrincipal)auth.getPrincipal();

        LoginUserDto loginUserDto = LoginUserDto.builder()
            .id(principal.getId())
            .loginUsername(principal.getUsername())
            .displayName(principal.getDisplayName())
            .role(principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .reduce("", (a, b) -> "".equals(a) ? b : a + "," + b))
            .token(principal.getToken())
            .build();
        return ResponseEntity.ok(loginUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserDto> login(@RequestParam String username, @RequestParam String password) throws LoginException {
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        Optional<LoginUserDto> loginResult = userAuthService.login(username, password);

        return ResponseEntity.ok(loginResult.orElseThrow(() -> new LoginException("Invalid username or password.")));
    }

//    @CrossOrigin(origins = "localhost:3000")
    @GetMapping("/user/{id}")
    public ResponseEntity<User> testGetUser(@PathVariable("id") Long id, HttpTrace.Principal p, Authentication auth) {
        boolean hasRoleTeacher = auth.getAuthorities().stream().anyMatch(a -> "ROLE_TEACHER".equals(a.getAuthority()));
        if (!hasRoleTeacher) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Optional<User> userOptional = userService.findById(id);
        return ResponseEntity.ok(userOptional.orElseThrow(() -> new RuntimeException("No User Found.")));
    }
}
