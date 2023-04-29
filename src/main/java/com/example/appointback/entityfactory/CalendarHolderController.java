package com.example.appointback.entityfactory;

import com.example.appointback.controller.DoctorMapper;
import com.example.appointback.controller.EmployeeMapper;
import com.example.appointback.controller.SchedulerMapper;
import com.example.appointback.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/CH")
@RequiredArgsConstructor
public class CalendarHolderController {

    private final CalendarHolderRepository repository;
    private final DoctorMapper doctorMapper;
    private final SchedulerMapper schedulerMapper;
    private final EmployeeMapper employeeMapper;

    @GetMapping("/{id}")
    public FactoryDtoOutput getCH(@PathVariable Long id) {
        return dtoFactory(repository.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/getAll")
    public List<FactoryDtoOutput> getAllCH() {
        return repository.findAll().stream().map(this::dtoFactory).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deleteCH(@PathVariable Long id) {
        repository.deleteById(id);
    }

    public FactoryDtoOutput dtoFactory(CalendarHolder entity) {
        switch (entity.getClass().getSimpleName()) {
            case "Doctor":
                return doctorMapper.mapToDoctorDto((Doctor) entity);
            case "Scheduler":
                return schedulerMapper.mapToSchedulerDto((Scheduler) entity);
            case "Employee":
                return employeeMapper.mapToEmployeeDto((Employee) entity);
            default:
                return null;
        }
    }
}
