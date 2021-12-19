package com.norriszhang.lisa.repository;

import com.norriszhang.lisa.datamodel.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;


public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByLoginId(String loginId);
}
