package com.norriszhang.lisa.bootstrap;

import com.norriszhang.lisa.datamodel.Clazz;
import com.norriszhang.lisa.datamodel.User;
import com.norriszhang.lisa.repository.ClassRepository;
import com.norriszhang.lisa.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

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
        User user = User.builder().loginId("lisa").password("password").role("TEACHER").build();
        userService.save(user);

        classRepository.save(Clazz.builder()
            .name("周六下午1.30pm 7-8岁")
            .description("")
            .dayOfWeek("SAT")
            .startTime(LocalTime.of(13, 30))
            .duration(90)
            .build());
        classRepository.save(Clazz.builder()
            .name("周日下午2.30pm 9-10岁")
            .description("")
            .dayOfWeek("SUN")
            .startTime(LocalTime.of(14, 30))
            .duration(90)
            .build());
        classRepository.save(Clazz.builder()
            .name("周一下午3.30pm 5-6岁")
            .description("")
            .dayOfWeek("MON")
            .startTime(LocalTime.of(15, 30))
            .duration(90)
            .build());
    }
}
