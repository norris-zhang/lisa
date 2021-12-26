package com.norriszhang.lisa.repository;

import com.norriszhang.lisa.datamodel.Clazz;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ClassRepository extends PagingAndSortingRepository<Clazz, Long> {
    @Override
    List<Clazz> findAll();
}
