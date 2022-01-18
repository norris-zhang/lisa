package com.norriszhang.lisa.repository;

import com.norriszhang.lisa.datamodel.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepository extends JpaRepository<Clazz, Long> {
}
