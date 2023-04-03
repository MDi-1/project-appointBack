package com.example.appointback.controller;

import com.example.appointback.entity.TestObjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/v1/test")
@RequiredArgsConstructor
public class TestObjectController {

    private final TestObjectRepository repository;
    private final TestObjectMapper mapper;

    @GetMapping("/{testObjId}")
    public TestObjectDto getTestObject(@PathVariable Long testObjId) {
        return mapper.mapToTestObjectDto(repository.findById(testObjId).orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/getAll")
    public List<TestObjectDto> getTestObjects() {
        return mapper.mapToTestObjectDtoList(repository.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TestObjectDto createTestObject(@RequestBody TestObjectDto dto) {
        return mapper.mapToTestObjectDto(repository.save(mapper.mapToTestObject(dto)));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TestObjectDto updateTestObject(@RequestBody TestObjectDto dto) {
        return mapper.mapToTestObjectDto(repository.save(mapper.mapToTestObject(dto)));
    }
}
