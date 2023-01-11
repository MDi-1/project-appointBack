package com.example.appointback.controller;

import com.example.appointback.entity.TestObject;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface TestObjectRepository extends CrudRepository<TestObject, Integer> {

    List<TestObject> findAll();

    Optional<TestObject> findByName(String name);
}
