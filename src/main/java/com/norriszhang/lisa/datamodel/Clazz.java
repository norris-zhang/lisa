package com.norriszhang.lisa.datamodel;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "lisa_class")
@Data
public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clas_id")
    private Long id;
    @Column(name = "clas_name")
    private String name;
    @Column(name = "clas_description")
    private String description;
    @Column(name = "clas_day_of_week")
    private String dayOfWeek;
    @Column(name = "clas_duration")
    private Integer duration;
}
