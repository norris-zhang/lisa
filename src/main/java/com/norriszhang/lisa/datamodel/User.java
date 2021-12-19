package com.norriszhang.lisa.datamodel;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table(name = "lisa_user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name = "user_login_id")
    private String loginId;
    @Column(name = "user_password")
    private String password;
    @Column(name = "user_role")
    private String role;
}
