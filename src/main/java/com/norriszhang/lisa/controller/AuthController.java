package com.norriszhang.lisa.controller;

import com.norriszhang.lisa.datamodel.User;
import com.norriszhang.lisa.service.UserAuthenticationService;
import com.norriszhang.lisa.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
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
    public ResponseEntity<String> checkLogin() {
        log.info("checkLogin...");
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        Optional<String> loginResult = userAuthService.login(username, password);
        return ResponseEntity.ok(loginResult.get());
    }

//    @CrossOrigin(origins = "localhost:3000")
    @GetMapping("/user/{id}")
    public ResponseEntity<User> testGetUser(@PathVariable("id") Long id, HttpTrace.Principal p, Authentication auth) {
        boolean hasRoleTeacher = auth.getAuthorities().stream().anyMatch(a -> "ROLE_TEACHER".equals(a.getAuthority()));
        if (!hasRoleTeacher) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Optional<User> userOptional = userService.findById(id);
        ResponseEntity<User> response = ResponseEntity.ok(userOptional.get());
        return response;
    }
}
