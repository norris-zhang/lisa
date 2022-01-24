package com.norriszhang.lisa.repository;

import com.norriszhang.lisa.datamodel.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {
}
