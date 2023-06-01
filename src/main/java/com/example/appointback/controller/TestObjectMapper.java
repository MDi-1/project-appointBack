package com.example.appointback.controller;

import com.example.appointback.entity.TestObject;
import com.example.appointback.entity.TestObjectDto;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestObjectMapper {

    public TestObject mapToTestObject(final TestObjectDto dto) {
        return new TestObject(dto.getId(), dto.getName(), dto.isTrueOrFalse(), dto.getRankLabel());
    }

    public TestObjectDto mapToTestObjectDto(final TestObject tstObj) {
        return new TestObjectDto(tstObj.getId(), tstObj.getName(), tstObj.isTrueOrFalse(), tstObj.getRankLabel());
    }

    public List<TestObjectDto> mapToTestObjectDtoList(final List<TestObject> testList) {
        return testList.stream().map(this::mapToTestObjectDto).collect(Collectors.toList());
    }
}