package com.norriszhang.lisa.service;

import com.norriszhang.lisa.datamodel.Clazz;
import com.norriszhang.lisa.repository.ClassRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepository;

    public ClassServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public List<Clazz> listClasses() {
        return classRepository.findAll();
    }
}
