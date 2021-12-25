package com.norriszhang.lisa.service;

import com.norriszhang.lisa.datamodel.User;
import com.norriszhang.lisa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id).map(user -> {
            user.setPassword(null);
            return user;
        });
    }
}
