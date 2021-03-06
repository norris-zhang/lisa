package com.norriszhang.lisa.datamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 2000)
    private String description;
    @Column(length = 3, name = "day_of_week")
    private String dayOfWeek;
    @Column(name = "start_time", columnDefinition = "TIME")
    private LocalTime startTime;
    private Integer duration;
    @OneToMany(mappedBy = "clazz")
    @Default
    private Set<StudentClass> students = new HashSet<>();
}
