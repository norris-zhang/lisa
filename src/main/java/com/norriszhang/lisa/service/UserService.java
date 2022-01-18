package com.norriszhang.lisa.service;

import com.norriszhang.lisa.datamodel.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByLoginId(String loginId);

    Optional<User> findById(Long id);

    User save(User user);
}
