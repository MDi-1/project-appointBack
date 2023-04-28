package com.example.appointback.controller;

import com.example.appointback.entity.CalendarHolder;
import com.example.appointback.entity.Doctor;
import com.example.appointback.entity.Scheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/ctrl")
@RequiredArgsConstructor
public class Controller {

    private final CHRepository repository;
    private final DoctorRepository doctorRepository;
    private final SchedulerRepository schedulerRepository;

    @GetMapping("/{id}")
    public CalendarHolder get(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @GetMapping("/getAll")
    public void getAllCH() {
        List<CalendarHolder> list = repository.findAll();
        System.out.println(list);
    }
}
