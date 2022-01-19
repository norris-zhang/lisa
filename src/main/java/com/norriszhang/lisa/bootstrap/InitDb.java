package com.norriszhang.lisa.bootstrap;

import com.norriszhang.lisa.datamodel.User;
import com.norriszhang.lisa.repository.ClassRepository;
import com.norriszhang.lisa.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"dev", "test"})
@Component
public class InitDb implements ApplicationRunner {
    private final ClassRepository classRepository;
    private final UserService userService;

    public InitDb(ClassRepository classRepository, UserService userService) {
        this.classRepository = classRepository;
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = User.builder()
            .loginId("lisa")
            .password("password")
            .role("TEACHER")
            .build();
        userService.save(user);

    }
}
