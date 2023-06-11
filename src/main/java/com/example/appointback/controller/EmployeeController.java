package com.example.appointback.controller;

import com.example.appointback.entity.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeMapper mapper;
    private final EmployeeRepository repository;

    @GetMapping("/{employeeId}")
    public EmployeeDto getEmployee(@PathVariable Long employeeId) throws IllegalArgumentException {
        return mapper.mapToEmployeeDto(repository.findById(employeeId).orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/getAll")
    public List<EmployeeDto> getEmployees() {
        return mapper.mapToEmployeeDtoList(repository.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto createEmployee(@RequestBody EmployeeDto dto) {
        return mapper.mapToNewEmployeeDto(repository.save(mapper.mapToEmployee(dto)));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto updateEmployee(@RequestBody EmployeeDto dto) {
        return mapper.mapToEmployeeDto(repository.save(mapper.mapToEmployee(dto)));
    }

    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@PathVariable Long employeeId) {
        repository.deleteById(employeeId);
    }
}
