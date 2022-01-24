package com.norriszhang.lisa.repository;

import com.norriszhang.lisa.datamodel.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
