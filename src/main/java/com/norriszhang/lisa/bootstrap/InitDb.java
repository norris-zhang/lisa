package com.norriszhang.lisa.bootstrap;

import com.norriszhang.lisa.datamodel.Clazz;
import com.norriszhang.lisa.datamodel.Student;
import com.norriszhang.lisa.datamodel.StudentClass;
import com.norriszhang.lisa.datamodel.User;
import com.norriszhang.lisa.repository.ClassRepository;
import com.norriszhang.lisa.repository.StudentClassRepository;
import com.norriszhang.lisa.repository.StudentRepository;
import com.norriszhang.lisa.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Calendar;

import static java.util.Calendar.FEBRUARY;
import static java.util.Calendar.MARCH;

@Profile({"dev", "test"})
@Component
public class InitDb implements ApplicationRunner {
    private final ClassRepository classRepository;
    private final UserService userService;
    private final StudentRepository studentRepository;
    private final StudentClassRepository studentClassRepository;
    private final String defaultZoneId;

    public InitDb(ClassRepository classRepository,
                  UserService userService,
                  StudentRepository studentRepository,
                  StudentClassRepository studentClassRepository,
                  @Value("${zoneid.default}") String defaultZoneId) {
        this.classRepository = classRepository;
        this.userService = userService;
        this.studentRepository = studentRepository;
        this.studentClassRepository = studentClassRepository;
        this.defaultZoneId = defaultZoneId;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = User.builder().loginId("lisa").displayName("Lisa Yao").password("password").role("TEACHER").build();
        userService.save(user);

        createClassSat130();
        createClassSun230();
        createClassMon330();
    }

    private void createClassMon330() {
        Clazz mon330 = Clazz.builder()
            .name("周一下午3.30pm 5-6岁")
            .description("")
            .dayOfWeek("MON")
            .startTime(LocalTime.of(15, 30))
            .duration(90)
            .build();
        classRepository.save(mon330);

        addStudentsToClass(mon330,
            Student.builder()
                .name("Dongchen Zhang")
                .dob(LocalDate.of(2014, MARCH, 28))
                .build());

        addStudentsToClass(mon330,
            Student.builder()
                .name("Dongyu Zhang")
                .dob(LocalDate.of(2014, MARCH, 28))
                .build());

        addStudentsToClass(mon330,
            Student.builder()
                .name("Isaac Bao")
                .dob(LocalDate.of(2014, FEBRUARY, 21))
                .build());

    }

    private void createClassSun230() {
        Clazz sun230 = Clazz.builder()
            .name("周日下午2.30pm 9-10岁")
            .description("")
            .dayOfWeek("SUN")
            .startTime(LocalTime.of(14, 30))
            .duration(90)
            .build();
        classRepository.save(sun230);

        addStudentsToClass(sun230,
            Student.builder()
                .name("Dongchen Zhang")
                .dob(LocalDate.of(2014, MARCH, 28))
                .build());

        addStudentsToClass(sun230,
            Student.builder()
                .name("Dongyu Zhang")
                .dob(LocalDate.of(2014, MARCH, 28))
                .build());

        addStudentsToClass(sun230,
            Student.builder()
                .name("Isaac Bao")
                .dob(LocalDate.of(2014, FEBRUARY, 21))
                .build());
    }

    private void createClassSat130() {
        Clazz sat130 = Clazz.builder()
            .name("周六下午1.30pm 7-8岁")
            .description("")
            .dayOfWeek("SAT")
            .startTime(LocalTime.of(13, 30))
            .duration(90)
            .build();
        classRepository.save(sat130);

        addStudentsToClass(sat130,
            Student.builder()
                .name("Dongchen Zhang")
                .dob(LocalDate.of(2014, MARCH, 28))
                .build());

        addStudentsToClass(sat130,
            Student.builder()
                .name("Dongyu Zhang")
                .dob(LocalDate.of(2014, MARCH, 28))
                .build());

        addStudentsToClass(sat130,
            Student.builder()
                .name("Isaac Bao")
                .dob(LocalDate.of(2014, FEBRUARY, 21))
                .build());
    }

    private void addStudentsToClass(Clazz clazz, Student... students) {
        Arrays.stream(students).forEach(s -> {
            StudentClass sc = StudentClass.builder()
                .student(s).clazz(clazz)
                .effectiveDate(ZonedDateTime.of(2021, Calendar.FEBRUARY, 21, 0, 0, 0, 0, ZoneId.of(this.defaultZoneId)))
                .sessionsLeft(9)
                .build();
            clazz.getStudents().add(sc);
            s.getClasses().add(sc);
            studentRepository.save(s);
            studentClassRepository.save(sc);
        });
        classRepository.save(clazz);
    }
}
