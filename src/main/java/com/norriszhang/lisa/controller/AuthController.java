package com.norriszhang.lisa.controller;

import com.norriszhang.lisa.datamodel.User;
import com.norriszhang.lisa.service.UserAuthenticationService;
import com.norriszhang.lisa.service.UserService;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserAuthenticationService userAuthService;
    private final UserService userService;

    public AuthController(UserAuthenticationService userAuthService, UserService userService) {
        this.userAuthService = userAuthService;
        this.userService = userService;
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        Optional<String> loginResult = userAuthService.login(username, password);
        return ResponseEntity.ok(loginResult.get());
    }

//    @CrossOrigin(origins = "localhost:3000")
    @GetMapping("/user/{id}")
    public ResponseEntity<User> testGetUser(@PathVariable("id") Long id, HttpTrace.Principal p, Authentication auth) {
        boolean hasRoleTeacher = auth.getAuthorities().stream().anyMatch(a -> "ROLE_TEACHER".equals(a.getAuthority()));
        if (hasRoleTeacher) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Optional<User> userOptional = userService.findById(id);
        ResponseEntity<User> response = ResponseEntity.ok(userOptional.get());
        return response;
    }
}
